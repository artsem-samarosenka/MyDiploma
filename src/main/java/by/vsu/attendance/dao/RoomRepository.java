package by.vsu.attendance.dao;

import by.vsu.attendance.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByFloor(int floor);

    Optional<Room> findByNumber(int number);
}
