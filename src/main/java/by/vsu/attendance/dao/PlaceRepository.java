package by.vsu.attendance.dao;

import by.vsu.attendance.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByNumberAndRoomId(int number, Long roomId);
}
