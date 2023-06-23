package by.vsu.attendance.dao;

import by.vsu.attendance.domain.Attendance;
import by.vsu.attendance.domain.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByPlace_Room_NumberAndAttendanceStatus(int number, AttendanceStatus attendanceStatus); // ???
}
