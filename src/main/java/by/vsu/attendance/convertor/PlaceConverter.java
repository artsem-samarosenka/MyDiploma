package by.vsu.attendance.convertor;

import by.vsu.attendance.domain.Place;
import by.vsu.attendance.domain.PlaceStatus;
import by.vsu.attendance.domain.Student;
import by.vsu.attendance.dto.PlaceDto;
import by.vsu.attendance.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PlaceConverter {

    private final StudentService studentService;

    public PlaceDto entityToDto(Place place) {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setNumber(place.getNumber());
        placeDto.setPlaceStatus(place.getPlaceStatus());
        // TODO not all student, only lastname
        Student student = studentService.getStudentByBookedPlace(place);
        if (student != null && place.getPlaceStatus() == PlaceStatus.BOOKED) {
            placeDto.setStudentSurname(student.getSurname());
        }
        return placeDto;
    }

    public List<PlaceDto> entityToDto(List<Place> places) {
        return places.stream().map(this::entityToDto).toList();
    }

    public Place dtoToEntity(PlaceDto placeDto) {
        Place place = new Place();
        place.setNumber(placeDto.getNumber());
        place.setPlaceStatus(placeDto.getPlaceStatus());
        return place;
    }

    public List<Place> dtoToEntity(List<PlaceDto> placeDtos) {
        return placeDtos.stream().map(this::dtoToEntity).toList();
    }
}
