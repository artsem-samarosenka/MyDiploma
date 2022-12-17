package by.vsu.attendance.services;

import by.vsu.attendance.dao.AttendanceRepository;
import by.vsu.attendance.dao.PlaceRepository;
import by.vsu.attendance.dao.RoomRepository;
import by.vsu.attendance.dao.UserRepository;
import by.vsu.attendance.domain.Attendance;
import by.vsu.attendance.domain.AttendanceStatus;
import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.PlaceStatus;
import by.vsu.attendance.domain.Room;
import by.vsu.attendance.domain.User;
import by.vsu.attendance.exceptions.PlaceBookingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class StudentService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final PlaceRepository placeRepository;
    private final AttendanceRepository attendanceRepository;

    @Transactional
    public void bookPlace(int roomNum, int placeNum, String accountId) {
        User user = userRepository.findByAccountId(accountId).orElseThrow();
        Room room = roomRepository.findByNumber(roomNum).orElseThrow();
        Place place = placeRepository.findByNumberAndRoomId(placeNum, room.getId()).orElseThrow();

        if (place.getPlaceStatus() == PlaceStatus.BOOKED) {
            log.info("{} is already BOOKED", place);
            throw new PlaceBookingException("Place is already booked");
        }

        // TODO check if user already booked a place

        Attendance attendance = new Attendance();
        attendance.setAttendanceStatus(AttendanceStatus.NOT_CONFIRMED);
        attendance.setPlaceId(place.getId());
        attendance.setUserId(user.getId());
        attendance.setDateTime(LocalDateTime.now());
        attendanceRepository.save(attendance);
        log.info("Saved attendance " + attendance);

        place.setPlaceStatus(PlaceStatus.BOOKED);
        placeRepository.save(place);
        log.info("Changed place {} status to {}", place, PlaceStatus.BOOKED);
    }
}
