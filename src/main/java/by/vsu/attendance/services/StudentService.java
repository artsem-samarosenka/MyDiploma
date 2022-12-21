package by.vsu.attendance.services;

import by.vsu.attendance.dao.AttendanceRepository;
import by.vsu.attendance.dao.PlaceRepository;
import by.vsu.attendance.dao.UserRepository;
import by.vsu.attendance.domain.Attendance;
import by.vsu.attendance.domain.AttendanceStatus;
import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.PlaceStatus;
import by.vsu.attendance.domain.User;
import by.vsu.attendance.exceptions.PlaceBookingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class StudentService {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final AttendanceRepository attendanceRepository;

    @Transactional
    public void bookPlace(int roomNum, int placeNum, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with username=" + username + " doesn't exist"));
        Place place = placeRepository.findByNumberAndRoomNumber(placeNum, roomNum)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Place with placeNum=%s and roomNum=% doesn't exist", placeNum, roomNum)
                ));

        if (place.getPlaceStatus() == PlaceStatus.BOOKED) {
            log.info("{} is already BOOKED", place);
            throw new PlaceBookingException("Place is already booked");
        }

        // TODO check if user already booked a place

        Attendance attendance = new Attendance();
        attendance.setAttendanceStatus(AttendanceStatus.NOT_CONFIRMED);
        attendance.setPlace(place);
        attendance.setUser(user);
        attendance.setDateTime(LocalDateTime.now());
        attendanceRepository.save(attendance);
        log.debug("Saved attendance " + attendance);

        place.setPlaceStatus(PlaceStatus.BOOKED);
        placeRepository.save(place);
        log.debug("Changed place {} status to {}", place, PlaceStatus.BOOKED);

        log.info("User {} booked place {} in the room {}", username, placeNum, roomNum);
    }
}
