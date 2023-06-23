package by.vsu.attendance.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.vsu.attendance.dao.AttendanceRepository;
import by.vsu.attendance.dao.PlaceRepository;
import by.vsu.attendance.dao.StudentRepository;
import by.vsu.attendance.domain.Attendance;
import by.vsu.attendance.domain.AttendanceStatus;
import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.PlaceStatus;
import by.vsu.attendance.domain.Room;
import by.vsu.attendance.domain.Student;
import by.vsu.attendance.exceptions.PlaceBookingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock private AttendanceRepository attendanceRepository;
    @Mock private PlaceRepository placeRepository;
    @Mock private StudentRepository studentRepository;

    private static LocalDateTime testDate;
    private static MockedStatic<LocalDateTime> localDateTimeMockedStatic;

    @BeforeAll
    public static void setUp() {
        testDate = LocalDateTime.now();
        localDateTimeMockedStatic = mockStatic(LocalDateTime.class);
    }

    @AfterAll
    public static void tearDown() {
        localDateTimeMockedStatic.close();
    }

    @Test
    void bookPlaceTest() {
        // given
        final int testRoomNumber = 300;
        final int testPlaceNumber = 12;
        final String testAccountId = "1901290090";

        final Student testStudent = new Student();
        testStudent.setAccountId(testAccountId);
        final Room testRoom = new Room();
        testRoom.setNumber(testRoomNumber);
        final Place testPlace = new Place();
        testPlace.setNumber(testPlaceNumber);

        final Attendance expectedAttendance = new Attendance(
                null,
                testStudent,
                testPlace,
                AttendanceStatus.NOT_CONFIRMED,
                testDate
        );

        localDateTimeMockedStatic
                .when(LocalDateTime::now)
                .thenReturn(testDate);
        when(studentRepository.findByAccountId(testAccountId)).thenReturn(Optional.of(testStudent));
        when(placeRepository.findByNumberAndRoomNumber(testPlaceNumber, testRoomNumber)).thenReturn(Optional.of(testPlace));

        // when
        studentService.bookPlace(testRoomNumber, testPlaceNumber, testAccountId);

        // then
        verify(attendanceRepository, times(1)).save(
                argThat((Attendance at) ->
                        at.getStudent().equals(expectedAttendance.getStudent()) &&
                                at.getPlace().equals(expectedAttendance.getPlace()) &&
                                at.getAttendanceStatus().equals(expectedAttendance.getAttendanceStatus()) &&
                                at.getDateTime().equals(expectedAttendance.getDateTime())
                ));
        assertEquals(PlaceStatus.BOOKED, testPlace.getPlaceStatus());
        verify(placeRepository, times(1)).save(testPlace);
    }

    @Test
    void bookAlreadyBookedPlace() {
        // given
        final int testRoomNumber = 300;
        final int testPlaceNumber = 12;
        final String testAccountId = "1901290090";

        final Student testStudent = new Student();
        final Place testPlace = new Place();
        testPlace.setPlaceStatus(PlaceStatus.BOOKED);

        when(studentRepository.findByAccountId(testAccountId)).thenReturn(Optional.of(testStudent));
        when(placeRepository.findByNumberAndRoomNumber(testPlaceNumber, testRoomNumber)).thenReturn(Optional.of(testPlace));

        // then
        assertThrows(PlaceBookingException.class,
                () -> studentService.bookPlace(testRoomNumber, testPlaceNumber, testAccountId));
    }
}