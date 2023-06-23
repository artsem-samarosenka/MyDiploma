package by.vsu.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentBookedPlaceResponse {

    private LocalDate date;
    private LocalTime time;
    private int floorNumber;
    private int roomNumber;
    private int placeNumber;
}
