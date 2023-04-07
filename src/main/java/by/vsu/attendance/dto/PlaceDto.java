package by.vsu.attendance.dto;

import by.vsu.attendance.domain.PlaceStatus;
import by.vsu.attendance.domain.User;
import lombok.Data;

@Data
public class PlaceDto {

    private int number;
    private PlaceStatus placeStatus;
    private User user;
}
