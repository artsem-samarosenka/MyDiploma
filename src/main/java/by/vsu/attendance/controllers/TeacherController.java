package by.vsu.attendance.controllers;

import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.Room;
import by.vsu.attendance.domain.User;
import by.vsu.attendance.services.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/rooms")
    public List<Room> getAllRooms(@RequestParam(required = false, defaultValue = "false") boolean sort) {
        return sort ? teacherService.getAllRoomsSorted() : teacherService.getAllRooms();
    }

    @GetMapping("/floors/{floor}/rooms")
    public List<Room> getAllRoomsOnTheFloor(@PathVariable @Positive int floor,
                                            @RequestParam(required = false, defaultValue = "false") boolean sort) {
        return sort ?
                teacherService.getAllRoomsOnTheFloorSorted(floor) :
                teacherService.getAllRoomsOnTheFloor(floor);
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

    @GetMapping("/rooms/{roomNum}/places")
    public List<Place> getAllPlacesInRoom(@PathVariable
                                          @Positive(message = "Room number should be positive")
                                          int roomNum) {
        // TODO
        return null;
    }

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
