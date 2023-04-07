package by.vsu.attendance.convertor;

import by.vsu.attendance.domain.Room;
import by.vsu.attendance.domain.Room;
import by.vsu.attendance.dto.RoomDto;
import by.vsu.attendance.dto.RoomDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomConverter {
    
    public RoomDto entityToDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setNumber(room.getNumber());
        return roomDto;
    }

    public List<RoomDto> entityToDto(List<Room> rooms) {
        return rooms.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Room dtoToEntity(RoomDto roomDto) {
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        return room;
    }

    public List<Room> dtoToEntity(List<RoomDto> roomDtos) {
        return roomDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
