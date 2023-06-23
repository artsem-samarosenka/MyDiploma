package by.vsu.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissedLessonsResponse {

    private int currentMissedLessons;
    private int maxMissedLessons;
}
