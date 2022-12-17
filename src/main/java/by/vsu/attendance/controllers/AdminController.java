package by.vsu.attendance.controllers;

import by.vsu.attendance.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/add-students")
    public void addStudents(@RequestBody MultipartFile file) {
        // TODO
    }
}
