package by.vsu.attendance.services;

import by.vsu.attendance.dao.PlaceRepository;
import by.vsu.attendance.dao.RoomRepository;
import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.Room;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class TeacherService {

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
        return roomRepository.findAllByFloor(floor);
    }

    public List<Room> getAllRoomsOnTheFloorSorted(int floor) {
        List<Room> rooms = getAllRoomsOnTheFloor(floor);
        rooms.sort(Comparator.comparingInt(Room::getNumber));
        return rooms;
    }

    public List<Place> getAllPlacesInTheRoom(int roomNum) {
        Room room = roomRepository.findByNumber(roomNum)
                .orElseThrow(() -> new NoSuchElementException("Room with roomNum=" + roomNum + " doesn't exist"));
        return placeRepository.findAllByRoom(room);
    }

    public List<Place> getAllPlacesInTheRoomSorted(int roomNum) {
        List<Place> places = getAllPlacesInTheRoom(roomNum);
        places.sort(Comparator.comparingInt(Place::getNumber));
        return places;
    }
}
