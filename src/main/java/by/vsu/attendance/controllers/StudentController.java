package by.vsu.attendance.controllers;

import by.vsu.attendance.domain.Place;
import by.vsu.attendance.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public String test() {
        return "Hello";
    }

    @GetMapping("/rooms/{roomNum}/places/free")
    public List<Place> getFreePlacesInRoom(@PathVariable
                                           @Positive(message = "Room number should be positive")
                                           int roomNum) {
        // TODO
        return null;
    }

    @PostMapping("/rooms/{roomNum}/places/{placeNum}/book")
    public void bookPlace(@PathVariable @Positive(message = "Room number should be positive") int roomNum,
                          @PathVariable @Positive(message = "Place number should be positive") int placeNum,
                          @RequestBody @NotEmpty(message = "AccountId shouldn't be empty") String accountId) {
        studentService.bookPlace(roomNum, placeNum, accountId);
    }

    @PostMapping("/free-place")
    public void freeAllPlaces(@RequestBody @NotEmpty(message = "AccountId shouldn't be empty") String accountId) {
        // TODO
    }

}
