package by.vsu.attendance.controllers;

import by.vsu.attendance.convertor.PlaceConverter;
import by.vsu.attendance.convertor.RoomConverter;
import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.Room;
import by.vsu.attendance.domain.User;
import by.vsu.attendance.dto.FloorDto;
import by.vsu.attendance.dto.PlaceDto;
import by.vsu.attendance.dto.RoomDto;
import by.vsu.attendance.services.TeacherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
//@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class TeacherController {

    private final TeacherService teacherService;
    private final PlaceConverter placeConverter;
    private final RoomConverter roomConverter;

    @GetMapping("/rooms")
    public List<RoomDto> getAllRooms(@RequestParam(required = false, defaultValue = "false") boolean sort) {
        log.debug("Getting all rooms.");
        List<Room> rooms = sort ? teacherService.getAllRoomsSorted() : teacherService.getAllRooms();
        log.info("Got {} number of rooms.", rooms.size());
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

    @GetMapping("/floors/{floorNum}/rooms")
    public List<RoomDto> getAllRoomsOnTheFloor(@PathVariable @Positive int floorNum,
                                            @RequestParam(required = false, defaultValue = "false") boolean sort) {
        log.debug("Start getting all rooms in {} floor", floorNum);
        List<Room> rooms = sort ?
                teacherService.getAllRoomsOnTheFloorSorted(floorNum) :
                teacherService.getAllRoomsOnTheFloor(floorNum);
        log.info("Got rooms in {} floor: {}", floorNum, rooms);
        return roomConverter.entityToDto(rooms);
    }

    @GetMapping("/rooms/{roomNum}/places")
    public List<PlaceDto> getAllPlacesInTheRoom(@PathVariable @Positive int roomNum,
                                                @RequestParam(required = false, defaultValue = "false") boolean sort) {
        log.debug("Getting all places in {} room", roomNum);
        List<Place> places = sort ?
                teacherService.getAllPlacesInTheRoomSorted(roomNum) :
                teacherService.getAllPlacesInTheRoom(roomNum);
        log.info("Got places in {} room: {}", roomNum, places);
        return placeConverter.entityToDto(places);
    }

    @GetMapping("/rooms/{roomNum}/students")
    public List<User> getAllStudentInRoom(@PathVariable
                                          @Positive(message = "Room number should be positive")
                                          int roomNum) {
        // TODO
        return null;
    }

    @GetMapping("/rooms/{roomNum}/students/present")
    public List<User> getPresentStudentsInRoom(@PathVariable
                                               @Positive(message = "Room number should be positive")
                                               int roomNum) {
        // TODO
        return null;
    }

    @GetMapping("/rooms/{roomNum}/students/absent")
    public List<User> getAbsentStudentsInRoom(@PathVariable
                                              @Positive(message = "Room number should be positive")
                                              int roomNum) {
        // TODO
        return null;
    }

//    @GetMapping("/rooms/{roomNum}/places")
//    public List<Place> getAllPlacesInRoom(@PathVariable
//                                          @Positive(message = "Room number should be positive")
//                                          int roomNum) {
//        // TODO
//        return null;
//    }

    @GetMapping("/rooms/{roomNum}/places/{placeNum}/student")
    public User getStudentByPlace(@PathVariable @Positive(message = "Room number should be positive") int roomNum,
                                  @PathVariable @Positive(message = "Place number should be positive") int placeNum) {
        // TODO
        return null;
    }

    @PostMapping("/rooms/{roomNum}/confirm")
    public void confirm(@PathVariable @Positive(message = "Room number should be positive") int roomNum) {
        // TODO
    }

    @PostMapping("/rooms/{roomNum}/places/{placeNum}/free")
    public void freePlace(@PathVariable @Positive(message = "Room number should be positive") int roomNum,
                          @PathVariable @Positive(message = "Place number should be positive") int placeNum) {
        // TODO
    }
}
