package by.vsu.attendance.controllers;

import by.vsu.attendance.domain.Student;
import by.vsu.attendance.dto.CurrentBookedPlaceResponse;
import by.vsu.attendance.dto.MissedLessonsResponse;
import by.vsu.attendance.dto.StudentInfoResponse;
import by.vsu.attendance.services.JwtService;
import by.vsu.attendance.services.StudentService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('STUDENT')")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final JwtService jwtService;

    @GetMapping
    public String test() {
        return "Hello";
    }

    @GetMapping("/student/info")
    public StudentInfoResponse getStudentInfo(
            @RequestHeader("Authorization") @NotBlank String authHeader
    ) {
        log.debug("Processing GET request for /student/info with Authorization header = '{}'", authHeader);
        final String username = jwtService.extractUsernameByAuthHeader(authHeader);
        Student student = studentService.getStudentByUsername(username);

        StudentInfoResponse studentInfoResponse = new StudentInfoResponse(
                student.getName(),
                student.getSurname(),
                student.getAccountId()
        );
        log.info("Return '{}' student info", studentInfoResponse);
        return studentInfoResponse;
    }

    @GetMapping("/students/{accountId}/missed-lessons")
    public MissedLessonsResponse getStudentMissedLessons(
            @PathVariable @NotBlank(message = "AccountId of a user shouldn't be blank") String accountId
    ) {
        log.debug("Processing GET request for /students/{}/missed-lessons", accountId);
        MissedLessonsResponse response = studentService.getStudentMissedLessons(accountId);
        log.info("Returning '{}'", response);
        return response;
    }

    @GetMapping("/students/{accountId}/current-place")
    public CurrentBookedPlaceResponse getStudentCurrentPlace(
            @PathVariable @NotBlank(message = "AccountId of a user shouldn't be blank") String accountId
    ) {
        log.debug("Processing GET request for /students/{}/current-place", accountId);
        CurrentBookedPlaceResponse response = studentService.getStudentCurrentBookedPlace(accountId);
        log.info("Returning '{}'", response);
        return response;
    }

    @PostMapping("/rooms/{roomNumber}/places/{placeNumber}/book")
    public boolean bookPlace(
            @PathVariable @Positive(message = "Room number should be positive") int roomNumber,
            @PathVariable @Positive(message = "Place number should be positive") int placeNumber,
            @RequestBody @NotBlank(message = "AccountId shouldn't be blank") String accountId
    ) {
        log.debug("Processing POST request for /rooms/{}/places/{}/book endpoint with accountId='{}'",
                roomNumber, placeNumber, accountId);
        boolean isSuccess = studentService.bookPlace(roomNumber, placeNumber, accountId);
        log.info("Booked place '{}' in room '{}' for student '{}'", placeNumber, roomNumber, accountId);
        return isSuccess;
    }

    @GetMapping("/rooms/{roomNumber}/places/free")
    public List<Integer> getNumbersOfFreePlacesInRoom(
            @PathVariable @Positive(message = "Room number should be positive") int roomNumber
    ) {
        log.debug("Processing GET request for /rooms/{}/places/free", roomNumber);
        List<Integer> freePlaces = studentService.getFreePlaceNumbersInRoom(roomNumber);
        log.info("Returning FREE Places '{}'", freePlaces);
        return freePlaces;
    }

    @PostMapping("/student/cancel")
    public boolean cancelPlace(
            @RequestBody @NotBlank(message = "AccountId shouldn't be blank") String accountId
    ) {
        log.debug("Processing POST request for /student/cancel with accountId='{}'", accountId);
        boolean isSuccess = studentService.cancelPlace(accountId);
        log.info("Student '{}' canceled his place", accountId);
        return isSuccess;
    }
}
