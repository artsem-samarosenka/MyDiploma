package by.vsu.attendance.controllers;

import by.vsu.attendance.convertor.PlaceConverter;
import by.vsu.attendance.convertor.RoomConverter;
import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.Room;
import by.vsu.attendance.dto.FloorDto;
import by.vsu.attendance.dto.PlaceDto;
import by.vsu.attendance.dto.RoomDto;
import by.vsu.attendance.services.TeacherService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final PlaceConverter placeConverter;
    private final RoomConverter roomConverter;

    @GetMapping("/rooms")
    public List<RoomDto> getAllRooms(
            @RequestParam(required = false, defaultValue = "false") boolean sort
    ) {
        log.debug("Processing GET request for /rooms with sort='{}'", sort);
        List<Room> rooms = sort ? teacherService.getAllRoomsSorted() : teacherService.getAllRooms();
        log.info("Returning '{}' number of Rooms.", rooms.size());
        return roomConverter.entityToDto(rooms);
    }

    @GetMapping("/floors")
    public List<FloorDto> getAllFloors() {
        // TODO think about needs of this method
        log.debug("Getting all floors.");
        FloorDto floorDto1 = new FloorDto();
        floorDto1.setNumber(1);
        FloorDto floorDto2 = new FloorDto();
        floorDto2.setNumber(2);
        FloorDto floorDto3 = new FloorDto();
        floorDto3.setNumber(3);
        FloorDto floorDto4 = new FloorDto();
        floorDto4.setNumber(4);
        FloorDto floorDto5 = new FloorDto();
        floorDto5.setNumber(5);
        log.info("Got floors: {}", List.of(floorDto1, floorDto2, floorDto3, floorDto4, floorDto5));
        return List.of(floorDto1, floorDto2, floorDto3, floorDto4, floorDto5);
    }

    @GetMapping("/floors/{floorNumber}/rooms")
    public List<RoomDto> getAllRoomsOnTheFloor(
            @PathVariable @Positive(message = "Floor number should be positive") int floorNumber,
            @RequestParam(required = false, defaultValue = "false") boolean sort
    ) {
        log.debug("Processing GET request for /floors/{}/rooms with sort='{}'", floorNumber, sort);
        List<Room> rooms = sort ?
                teacherService.getAllRoomsOnTheFloorSorted(floorNumber) :
                teacherService.getAllRoomsOnTheFloor(floorNumber);
        log.info("Returning '{}' number of Rooms in Floor '{}'", rooms.size(), floorNumber);
        return roomConverter.entityToDto(rooms);
    }

    @GetMapping("/rooms/{roomNumber}/places")
    public List<PlaceDto> getAllPlacesInTheRoom(
            @PathVariable @Positive(message = "Room number should be positive") int roomNumber,
            @RequestParam(required = false, defaultValue = "false") boolean sort
    ) {
        log.debug("Processing GET request for /rooms/{}/places with sort='{}'", roomNumber, sort);
        List<Place> places = sort ?
                teacherService.getAllPlacesInTheRoomSorted(roomNumber) :
                teacherService.getAllPlacesInTheRoom(roomNumber);
        log.info("Returning '{}' number of Places in Room '{}'", places.size(), roomNumber);
        return placeConverter.entityToDto(places);
    }

    @PostMapping("/rooms/{roomNumber}/places/{placeNumber}/free")
    public void freePlace(
            @PathVariable @Positive(message = "Room number should be positive") int roomNumber,
            @PathVariable @Positive(message = "Place number should be positive") int placeNumber
    ) {
        log.debug("Processing POST request for /rooms/{}/places/{}/free", roomNumber, placeNumber);
        teacherService.freePlace(roomNumber, placeNumber);
        log.info("Made FREE Place '{}' in Room '{}'", placeNumber, roomNumber);
    }

    @PostMapping("/rooms/{roomNumber}/places/free")
    public void freeAllPlaces(
            @PathVariable @Positive(message = "Room number should be positive") int roomNumber
    ) {
        log.debug("Processing POST request for /rooms/{}/places/free", roomNumber);
        teacherService.freeAllPlace(roomNumber);
        log.info("Freed all places in Room '{}'", roomNumber);
    }

    @PostMapping("/rooms/{roomNumber}/approve")
    public void confirmAttendances(
            @PathVariable @Positive(message = "Room number should be positive") int roomNumber
    ) {
        log.debug("Processing POST request for /rooms/{}/confirm", roomNumber);
        teacherService.approveAttendances(roomNumber);
        // TODO we approved all attendances. Even if that attendances from yesterday. Fix it
        log.info("Approved all attendances in '{}' room", roomNumber);
    }

//    @GetMapping("/rooms/{roomNumber}/students")
//    public List<User> getAllStudentInRoom(@PathVariable
//                                          @Positive(message = "Room number should be positive")
//                                          int roomNumber) {
//        // TODO
//        return null;
//    }
//
//    @GetMapping("/rooms/{roomNumber}/students/present")
//    public List<User> getPresentStudentsInRoom(@PathVariable
//                                               @Positive(message = "Room number should be positive")
//                                               int roomNumber) {
//        // TODO
//        return null;
//    }
//
//    @GetMapping("/rooms/{roomNumber}/students/absent")
//    public List<User> getAbsentStudentsInRoom(@PathVariable
//                                              @Positive(message = "Room number should be positive")
//                                              int roomNumber) {
//        // TODO
//        return null;
//    }
//
////    @GetMapping("/rooms/{roomNumber}/places")
////    public List<Place> getAllPlacesInRoom(@PathVariable
////                                          @Positive(message = "Room number should be positive")
////                                          int roomNumber) {
////        // TODO
////        return null;
////    }
//
//    @GetMapping("/rooms/{roomNumber}/places/{placeNumber}/student")
//    public User getStudentByPlace(@PathVariable @Positive(message = "Room number should be positive") int roomNumber,
//                                  @PathVariable @Positive(message = "Place number should be positive") int placeNumber) {
//        // TODO
//        return null;
//    }
//
}
