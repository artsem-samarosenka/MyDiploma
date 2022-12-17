package by.vsu.attendance.services;

import by.vsu.attendance.dao.RoomRepository;
import by.vsu.attendance.domain.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private TeacherService teacherService;

    @Test
    void getAllRoomsTest() {
        // given
        List<Room> testRooms = Arrays.asList(
                new Room(1L, 100, 20, 1),
                new Room(2L, 200, 15, 2),
                new Room(3L, 300, 30, 3),
                new Room(4L, 201, 25, 2)
        );
        List<Room> expectedRooms = Arrays.asList(
                new Room(1L, 100, 20, 1),
                new Room(2L, 200, 15, 2),
                new Room(3L, 300, 30, 3),
                new Room(4L, 201, 25, 2)
        );

        when(roomRepository.findAll()).thenReturn(testRooms);

        // when
        List<Room> actualRooms = teacherService.getAllRooms();

        // then
        assertEquals(expectedRooms, actualRooms);
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void getAllRoomsSortedTest() {
        // given
        List<Room> testRooms = Arrays.asList(
                new Room(1L, 100, 20, 1),
                new Room(2L, 201, 15, 2),
                new Room(3L, 300, 30, 3),
                new Room(4L, 200, 25, 2)
        );
        List<Room> expectedRooms = Arrays.asList(
                new Room(1L, 100, 20, 1),
                new Room(4L, 200, 25, 2),
                new Room(2L, 201, 15, 2),
                new Room(3L, 300, 30, 3)
        );

        when(roomRepository.findAll()).thenReturn(testRooms);

        // when
        List<Room> actualRooms = teacherService.getAllRoomsSorted();

        // then
        assertEquals(expectedRooms, actualRooms);
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void getAllRoomsSortedEmptyTest() {
        // given
        List<Room> testRooms = new ArrayList<>();
        List<Room> expectedRooms = new ArrayList<>();

        when(roomRepository.findAll()).thenReturn(testRooms);

        // when
        List<Room> actualRooms = teacherService.getAllRoomsSorted();

        // then
        assertEquals(expectedRooms, actualRooms);
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void getAllRoomsOnTheFloorTest() {
        // given
        List<Room> testRooms = Arrays.asList(
                new Room(2L, 200, 15, 2),
                new Room(3L, 210, 30, 2),
                new Room(4L, 201, 25, 2)
        );
        List<Room> expectedRooms = Arrays.asList(
                new Room(2L, 200, 15, 2),
                new Room(3L, 210, 30, 2),
                new Room(4L, 201, 25, 2)
        );
        int floor = 2;

        when(roomRepository.findAllByFloor(floor)).thenReturn(testRooms);

        // when
        List<Room> actualRooms = teacherService.getAllRoomsOnTheFloor(floor);

        // then
        assertEquals(expectedRooms, actualRooms);
        verify(roomRepository, times(1)).findAllByFloor(floor);
    }

    @Test
    void getAllRoomsOnTheFloorSortedTest() {
        // given
        List<Room> testRooms = Arrays.asList(
                new Room(2L, 200, 15, 2),
                new Room(3L, 210, 30, 2),
                new Room(4L, 201, 25, 2)
        );
        List<Room> expectedRooms = Arrays.asList(
                new Room(2L, 200, 15, 2),
                new Room(4L, 201, 25, 2),
                new Room(3L, 210, 30, 2)
        );
        int floor = 2;

        when(roomRepository.findAllByFloor(floor)).thenReturn(testRooms);

        // when
        List<Room> actualRooms = teacherService.getAllRoomsOnTheFloorSorted(floor);

        // then
        assertEquals(expectedRooms, actualRooms);
        verify(roomRepository, times(1)).findAllByFloor(floor);
    }

    @Test
    void getAllRoomsByTheFloorSortedEmptyTest() {
        // given
        List<Room> testRooms = new ArrayList<>();
        List<Room> expectedRooms = new ArrayList<>();
        int floor = 2;

        when(roomRepository.findAllByFloor(2)).thenReturn(testRooms);

        // when
        List<Room> actualRooms = teacherService.getAllRoomsOnTheFloorSorted(floor);

        // then
        assertEquals(expectedRooms, actualRooms);
        verify(roomRepository, times(1)).findAllByFloor(floor);
    }
}