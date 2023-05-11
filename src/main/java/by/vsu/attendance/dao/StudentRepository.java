package by.vsu.attendance.dao;

import by.vsu.attendance.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByAccountId(String accountId);
}