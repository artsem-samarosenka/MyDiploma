package by.vsu.attendance.services;

import by.vsu.attendance.dao.AttendanceRepository;
import by.vsu.attendance.dao.PlaceRepository;
import by.vsu.attendance.dao.UserRepository;
import by.vsu.attendance.domain.Attendance;
import by.vsu.attendance.domain.AttendanceStatus;
import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.PlaceStatus;
import by.vsu.attendance.domain.Room;
import by.vsu.attendance.domain.User;
import by.vsu.attendance.domain.UserRole;
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

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private PlaceRepository placeRepository;

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

//    @Test
//    void bookPlaceTest() {
//        // given
//        int roomNum = 300;
//        int placeNum = 12;
//        String username = "1901290090";
//
//        User testUser = new User(1L, username, "pass", UserRole.STUDENT, null, false);
//        Room testRoom = new Room(1L, null, roomNum, 20, 3);
//        Place testPlace = new Place(1L, testRoom, null, placeNum, PlaceStatus.FREE);
//
//        Attendance expectedAttendance = new Attendance(
//                1L,
//                testUser,
//                testPlace,
//                AttendanceStatus.NOT_CONFIRMED,
//                testDate,
//                true
//        );
//
//        localDateTimeMockedStatic
//                .when(LocalDateTime::now)
//                .thenReturn(testDate);
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
//        when(placeRepository.findByNumberAndRoomNumber(placeNum, roomNum)).thenReturn(Optional.of(testPlace));
//
//        // when
//        studentService.bookPlace(roomNum, placeNum, username);
//
//        // then
//        verify(attendanceRepository, times(1)).save(
//                argThat((Attendance at) ->
//                        at.getUser().equals(expectedAttendance.getUser()) &&
//                        at.getPlace().equals(expectedAttendance.getPlace()) &&
//                        at.getAttendanceStatus().equals(expectedAttendance.getAttendanceStatus()) &&
//                        at.getDateTime().equals(expectedAttendance.getDateTime())
//                ));
//        verify(placeRepository, times(1)).save(testPlace);
//    }

//    @Test
//    void bookAlreadyBookedPlace() {
//        // given
//        int roomNum = 300;
//        int placeNum = 12;
//        String username = "1901290090";
//
//        User testUser = new User(1L, username, "pass", UserRole.STUDENT);
//        Room testRoom = new Room(1L, roomNum, 20, 3, null);
//        Place testPlace = new Place(1L, placeNum, PlaceStatus.BOOKED, testRoom);
//        testRoom.setPlaces(List.of(testPlace));
//
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
//        when(roomRepository.findByNumber(roomNum)).thenReturn(Optional.of(testRoom));
//        when(placeRepository.findByNumberAndRoomId(placeNum, testRoom.getId())).thenReturn(Optional.of(testPlace));
//
//        // then
//        Assertions.assertThrows(PlaceBookingException.class,
//                () -> studentService.bookPlace(roomNum, placeNum, username));
//    }
}