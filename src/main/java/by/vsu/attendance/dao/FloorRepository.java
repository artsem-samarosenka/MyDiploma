package by.vsu.attendance.dao;

import by.vsu.attendance.domain.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FloorRepository extends JpaRepository<Floor, Long> {
}