package by.vsu.attendance.services;

import by.vsu.attendance.dao.AttendanceRepository;
import by.vsu.attendance.dao.PlaceRepository;
import by.vsu.attendance.dao.RoomRepository;
import by.vsu.attendance.dao.StudentRepository;
import by.vsu.attendance.domain.Attendance;
import by.vsu.attendance.domain.AttendanceStatus;
import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.PlaceStatus;
import by.vsu.attendance.domain.Room;
import by.vsu.attendance.domain.Student;
import by.vsu.attendance.dto.CurrentBookedPlaceResponse;
import by.vsu.attendance.dto.MissedLessonsResponse;
import by.vsu.attendance.exceptions.PlaceBookingException;
import by.vsu.attendance.exceptions.StudentHasBookedPlace;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;
    private final PlaceRepository placeRepository;
    private final AttendanceRepository attendanceRepository;

    /**
     * Return Student by his Username.
     *
     * @param username username of a Student
     * @return Student
     */
    public Student getStudentByUsername(String username) {
        return studentRepository.findByUserUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Student with username='" + username + "' doesn't exist"));
    }

    /**
     * Book a place for a student by his accountId, room number and place number.
     * Place is available for booking ONLY if it is FREE and if the student doesn't have another booked place.
     * After booking a place we set PlaceStatus = BOOKED, and create new {@link Attendance} with AttendanceStatus = NOT_CONFIRMED.
     *
     * @param roomNumber  number of student room
     * @param placeNumber number of student place
     * @param accountId   student's accountId
     * @return true - success
     */
    @Transactional
    public boolean bookPlace(int roomNumber, int placeNumber, String accountId) {
        Student student = studentRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NoSuchElementException("Student with accountId='" + accountId + "' doesn't exist"));
        Place place = placeRepository.findByNumberAndRoomNumber(placeNumber, roomNumber)
                .orElseThrow(() -> new NoSuchElementException(
                        "Place with placeNumber='%s' and roomNumber='%s' doesn't exist".formatted(placeNumber, roomNumber))
                );

        if (place.getPlaceStatus() == PlaceStatus.BOOKED) {
            log.warn("Place '{}' is already BOOKED", place);
            throw new PlaceBookingException("Place is already booked");
        }

        if (!getStudentCurrentBookedPlace(student).equals(new CurrentBookedPlaceResponse())) {
            log.warn("Student '{}' already has BOOKED place", accountId);
            throw new StudentHasBookedPlace("Student already has BOOKED place");
        }

        Attendance attendance = new Attendance(
                null,
                student,
                place,
                AttendanceStatus.NOT_CONFIRMED,
                LocalDateTime.now()
        );
        attendanceRepository.save(attendance);
        log.debug("Saved attendance " + attendance);

        place.setPlaceStatus(PlaceStatus.BOOKED);
        placeRepository.save(place);
        log.debug("Changed place {} status to {}", place, PlaceStatus.BOOKED);

        log.info("Student {} booked place {} in the room {}", student.getAccountId(), placeNumber, roomNumber);
        return true;
    }

    /**
     * Cancel booked place by student accountId.
     *
     * @param accountId student's accountId
     * @return true if success
     */
    @Transactional
    public boolean cancelPlace(String accountId) {
        CurrentBookedPlaceResponse response = getStudentCurrentBookedPlace(accountId);
        Place place = placeRepository.findByNumberAndRoomNumber(response.getPlaceNumber(), response.getRoomNumber())
                .orElseThrow(() -> new NoSuchElementException(
                        "Place with placeNumber='%s' and roomNumber='%s' doesn't exist".formatted(response.getPlaceNumber(), response.getRoomNumber()))
                );
        place.setPlaceStatus(PlaceStatus.FREE);
        return true;
    }

    /**
     * Return {@link Student} by his booked place or null if it is FREE.
     * To find student who booked a place we use a fact that booked place has AttendanceStatus = NOT_CONFIRMED.
     *
     * @param place place to check
     * @return {@link Student} that booked a place or null if it is FREE
     */
    public Student getStudentByBookedPlace(Place place) {
        Attendance latestAttendance = place.getAttendances()
                .stream()
                .max(Comparator.comparing(Attendance::getDateTime))
                .orElse(null);

        if (latestAttendance == null) {
            log.debug("Place '{}' didn't ever have any booked places", place.getNumber());
            return null;
        }

        return latestAttendance.getStudent();
    }

    /**
     * Return a Student's current number of missed lessons and a maximum number of missed lessons.
     *
     * @param accountId student accountId
     * @return {@link MissedLessonsResponse}
     */
    public MissedLessonsResponse getStudentMissedLessons(String accountId) {
        Student student = studentRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NoSuchElementException("Student with accountId='" + accountId + "' doesn't exist"));
        return new MissedLessonsResponse(
                student.getCurrentMissedLessons(),
                student.getMaxMissedLessons()
        );
    }

    /**
     * Overloaded method for {@link StudentService#getStudentCurrentBookedPlace(Student)}.
     * This method is needed to eliminate possible extra invocation of DAO.
     *
     * @param accountId account ID of a student
     * @return {@link CurrentBookedPlaceResponse} or null if a student doesn't have any booked places
     * @see StudentService#getStudentCurrentBookedPlace(Student)
     */
    public CurrentBookedPlaceResponse getStudentCurrentBookedPlace(String accountId) {
        Student student = studentRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NoSuchElementException("Student with accountId='" + accountId + "' doesn't exist"));

        return getStudentCurrentBookedPlace(student);
    }

    /**
     * Return info about Student's current booked place or null if he hasn't any.
     * We get the latest Attendance that Student has. Then get Place of that Attendance and check its status.
     * <p>
     * We check the latest Attendance because only in it Student can have a booked place.
     * That's happening because new Attendance is created only after booking a place by Student.
     * And in order to book a place Student need to unbook his previous place.
     *
     * @param student student to check his booked place
     * @return {@link CurrentBookedPlaceResponse} or null if Student doesn't have any booked places
     */
    private CurrentBookedPlaceResponse getStudentCurrentBookedPlace(Student student) {
        Attendance latestAttendance = student.getAttendances()
                .stream()
                .max(Comparator.comparing(Attendance::getDateTime))
                .orElse(null);

        if (latestAttendance == null) {
            log.debug("Student with accountId='{}' didn't ever have any booked places", student.getAccountId());
            return new CurrentBookedPlaceResponse();
        }

        Place latestPlace = latestAttendance.getPlace();
        if (latestPlace.getPlaceStatus() == PlaceStatus.FREE) {
            log.debug("Student with accountId='{}' doesn't have any booked places", student.getAccountId());
            return new CurrentBookedPlaceResponse();
        }

        return new CurrentBookedPlaceResponse(
                latestAttendance.getDateTime().toLocalDate(),
                latestAttendance.getDateTime().toLocalTime(),
                latestPlace.getRoom().getFloor().getNumber(),
                latestPlace.getRoom().getNumber(),
                latestPlace.getNumber()
        );
    }

    /**
     * Return integer numbers of FREE places in a room.
     *
     * @param roomNumber number of a room
     * @return numbers of free places
     */
    public List<Integer> getFreePlaceNumbersInRoom(int roomNumber) {
        Room room = roomRepository.findByNumber(roomNumber)
                .orElseThrow(() -> new NoSuchElementException("Room with number='" + roomNumber + "' doesn't exist"));
        return room.getPlaces()
                .stream()
                .filter(place -> place.getPlaceStatus() == PlaceStatus.FREE)
                .map(Place::getNumber)
                .toList();
    }
}
