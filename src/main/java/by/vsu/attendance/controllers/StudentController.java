package by.vsu.attendance.controllers;

import by.vsu.attendance.domain.Place;
import by.vsu.attendance.services.StudentService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public String test() {
        return "Hello";
    }

    @GetMapping("/rooms/{room}/places/free")
    public List<Place> getFreePlacesInRoom(@PathVariable
                                           @Positive(message = "Room number should be positive")
                                           int room) {
        // TODO
        return null;
    }

    @PostMapping("/rooms/{roomNum}/places/{placeNum}/book")
    public void bookPlace(@PathVariable @Positive(message = "Room number should be positive") int roomNum,
                          @PathVariable @Positive(message = "Place number should be positive") int placeNum,
                          @RequestBody @NotEmpty(message = "Username shouldn't be empty") String username) {
        log.info("Booking {} place in {} room for {}", placeNum, roomNum, username);
        studentService.bookPlace(roomNum, placeNum, username);
    }

    @PostMapping("/free-place")
    public void freeAllPlaces(@RequestBody @NotEmpty(message = "Username shouldn't be empty") String username) {
        // TODO
    }

}
