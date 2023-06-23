package by.vsu.attendance.dao;

import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByNumberAndRoomNumber(int placeNumber, int roomNumber);
    List<Place> findAllByRoom(Room room);
}
