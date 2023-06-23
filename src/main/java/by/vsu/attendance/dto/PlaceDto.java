package by.vsu.attendance.dto;

import by.vsu.attendance.domain.PlaceStatus;
import lombok.Data;

@Data
public class PlaceDto {

    private int number;
    private PlaceStatus placeStatus;
    private String studentSurname;
}
