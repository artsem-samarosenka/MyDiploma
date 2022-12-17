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
import by.vsu.attendance.domain.UserRole;
import by.vsu.attendance.exceptions.PlaceBookingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
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
    private RoomRepository roomRepository;
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

    @Test
    void bookPlaceTest() {
        // given
        int roomNum = 300;
        int placeNum = 12;
        String accountId = "1901290090";

        User testUser = new User(1L, accountId, "pass", UserRole.STUDENT);
        Room testRoom = new Room(1L, roomNum, 20, 3);
        Place testPlace = new Place(1L, placeNum, PlaceStatus.FREE, testRoom.getId());

        Attendance expectedAttendance = new Attendance(
                1L,
                testUser.getId(),
                testPlace.getId(),
                AttendanceStatus.NOT_CONFIRMED,
                testDate
        );

        localDateTimeMockedStatic
                .when(LocalDateTime::now)
                .thenReturn(testDate);
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.of(testUser));
        when(roomRepository.findByNumber(roomNum)).thenReturn(Optional.of(testRoom));
        when(placeRepository.findByNumberAndRoomId(placeNum, testRoom.getId())).thenReturn(Optional.of(testPlace));

        // when
        studentService.bookPlace(roomNum, placeNum, accountId);

        // then
        verify(attendanceRepository, times(1)).save(
                argThat((Attendance at) ->
                        at.getUserId().equals(expectedAttendance.getUserId()) &&
                        at.getPlaceId().equals(expectedAttendance.getPlaceId()) &&
                        at.getAttendanceStatus().equals(expectedAttendance.getAttendanceStatus()) &&
                        at.getDateTime().equals(expectedAttendance.getDateTime())
                ));
        verify(placeRepository, times(1)).save(testPlace);
    }

    @Test
    void bookAlreadyBookedPlace() {
        // given
        int roomNum = 300;
        int placeNum = 12;
        String accountId = "1901290090";

        User testUser = new User(1L, accountId, "pass", UserRole.STUDENT);
        Room testRoom = new Room(1L, roomNum, 20, 3);
        Place testPlace = new Place(1L, placeNum, PlaceStatus.BOOKED, testRoom.getId());

        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.of(testUser));
        when(roomRepository.findByNumber(roomNum)).thenReturn(Optional.of(testRoom));
        when(placeRepository.findByNumberAndRoomId(placeNum, testRoom.getId())).thenReturn(Optional.of(testPlace));

        // then
        Assertions.assertThrows(PlaceBookingException.class,
                () -> studentService.bookPlace(roomNum, placeNum, accountId));
    }
}