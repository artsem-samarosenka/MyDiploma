package by.vsu.attendance.controllers;

import by.vsu.attendance.domain.Admin;
import by.vsu.attendance.domain.Student;
import by.vsu.attendance.domain.Teacher;
import by.vsu.attendance.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/students")
    public List<Student> getStudents() {
        log.info("Returning all students");
        return adminService.getAllStudents();
    }

    @PostMapping("/students")
    public void addStudent(@RequestBody Student student) {
        log.info("Adding new student '{}'", student);
        adminService.saveStudent(student);
    }

    @DeleteMapping("/students/{accountId}")
    public void deleteStudent(@PathVariable String accountId) {
        log.info("Deleting student with accountId='{}'", accountId);
        adminService.deleteStudentByAccountId(accountId);
    }

    @GetMapping("/teachers")
    public List<Teacher> getTeachers() {
        log.info("Returning all teachers");
        return adminService.getAllTeachers();
    }

    @PostMapping("/teachers")
    public void addTeacher(@RequestBody Teacher teacher) {
        log.info("Adding new teacher '{}'", teacher);
        adminService.saveTeacher(teacher);
    }

    @DeleteMapping("/teachers/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        log.info("Deleting teacher with id='{}'", id);
        adminService.deleteTeacherById(id);
    }

    @GetMapping("/admins")
    public List<Admin> getAdmins() {
        log.info("Returning all admins");
        return adminService.getAllAdmins();
    }

    @PostMapping("/admins")
    public void addAdmin(@RequestBody Admin admin) {
        log.info("Adding new admin '{}'", admin);
        adminService.saveAdmin(admin);
    }

    @DeleteMapping("/admins/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        log.info("Deleting admin with id='{}'", id);
        adminService.deleteAdminById(id);
    }

    @PostMapping("/students/file")
    public void addStudentsFromFile(@RequestBody MultipartFile file) {
        // TODO
    }
}
