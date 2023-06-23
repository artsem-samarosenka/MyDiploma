package by.vsu.attendance.services;

import by.vsu.attendance.dao.AdminRepository;
import by.vsu.attendance.dao.StudentRepository;
import by.vsu.attendance.dao.TeacherRepository;
import by.vsu.attendance.domain.Admin;
import by.vsu.attendance.domain.Student;
import by.vsu.attendance.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudentByAccountId(String accountId) {
        studentRepository.deleteByAccountId(accountId);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public void deleteAdminById(Long id) {
        adminRepository.deleteById(id);
    }
}
