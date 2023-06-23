package by.vsu.attendance.services;

import by.vsu.attendance.dao.AttendanceRepository;
import by.vsu.attendance.dao.PlaceRepository;
import by.vsu.attendance.dao.RoomRepository;
import by.vsu.attendance.domain.Attendance;
import by.vsu.attendance.domain.AttendanceStatus;
import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.PlaceStatus;
import by.vsu.attendance.domain.Room;
import by.vsu.attendance.exceptions.FreePlaceException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class TeacherService {

    private final AttendanceRepository attendanceRepository;
    private final RoomRepository roomRepository;
    private final PlaceRepository placeRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAllRoomsSorted() {
        List<Room> rooms = getAllRooms();
        rooms.sort(Comparator.comparingInt(Room::getNumber));
        return rooms;
    }

    public List<Room> getAllRoomsOnTheFloor(int floor) {
        return roomRepository.findByFloorNumber(floor);
    }

    public List<Room> getAllRoomsOnTheFloorSorted(int floor) {
        List<Room> rooms = getAllRoomsOnTheFloor(floor);
        rooms.sort(Comparator.comparingInt(Room::getNumber));
        return rooms;
    }

    public List<Place> getAllPlacesInTheRoom(int roomNumber) {
        Room room = roomRepository.findByNumber(roomNumber)
                .orElseThrow(() -> new NoSuchElementException("Room with roomNumber=" + roomNumber + " doesn't exist"));
        return room.getPlaces().stream().toList();
    }

    public List<Place> getAllPlacesInTheRoomSorted(int roomNumber) {
        List<Place> places = getAllPlacesInTheRoom(roomNumber);
        places.sort(Comparator.comparingInt(Place::getNumber));
        return places;
    }

    public void freePlace(int roomNumber, int placeNumber) {
        Place place = placeRepository.findByNumberAndRoomNumber(placeNumber, roomNumber)
                .orElseThrow(() -> new NoSuchElementException(
                        "Place with placeNumber='%s' and roomNumber='%s' doesn't exist".formatted(placeNumber, roomNumber)
                ));
        if (place.getPlaceStatus() != PlaceStatus.BOOKED) {
            log.error("Place with placeNumber='{}' and roomNumber='{}' is not BOOKED", placeNumber, roomNumber);
            throw new FreePlaceException(
                    "Place with placeNumber='%s' and roomNumber='%s' is not BOOKED".formatted(placeNumber, roomNumber)
            );
        }
        place.setPlaceStatus(PlaceStatus.FREE);
        // TODO Student still has booked place even after free
        placeRepository.save(place);
    }

    public void freeAllPlace(int roomNumber) {
        Room room = roomRepository.findByNumber(roomNumber)
                .orElseThrow(() -> new NoSuchElementException("Room with roomNumber='" + roomNumber + "' doesn't exist"));
        Set<Place> places = room.getPlaces();
        places.forEach(place -> place.setPlaceStatus(PlaceStatus.FREE));
        // TODO Student still has booked place even after free
        placeRepository.saveAll(places);
    }

    @Transactional
    public void approveAttendances(int roomNumber) {
        Room room = roomRepository.findByNumber(roomNumber)
                .orElseThrow(() -> new NoSuchElementException("Room with roomNumber='" + roomNumber + "' doesn't exist"));
        List<Place> bookedPlaces = room.getPlaces()
                .stream()
                .filter(place -> place.getPlaceStatus() == PlaceStatus.BOOKED)
                .toList();

        List<Attendance> attendancesToUpdate = new ArrayList<>();
        List<Place> placesToUpdate = new ArrayList<>();
        // TODO Improve this code? Because I make N queries to DB. Maybe do it with DB query/repo?
        for (Place place : bookedPlaces) {
            place.getAttendances()
                    .stream()
                    .filter(at -> at.getAttendanceStatus() == AttendanceStatus.NOT_CONFIRMED)
                    .forEach(at -> {
                        at.getPlace().setPlaceStatus(PlaceStatus.FREE);
                        at.setAttendanceStatus(AttendanceStatus.CONFIRMED);
                        placesToUpdate.add(at.getPlace());
                        attendancesToUpdate.add(at);
                    });
        }

        attendanceRepository.saveAll(attendancesToUpdate);
        placeRepository.saveAll(placesToUpdate);
    }
}
