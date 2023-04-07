package by.vsu.attendance.convertor;

import by.vsu.attendance.domain.Place;
import by.vsu.attendance.dto.PlaceDto;
import by.vsu.attendance.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PlaceConverter {

    private final StudentService studentService;

    public PlaceDto entityToDto(Place place) {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setPlaceStatus(place.getPlaceStatus());
        placeDto.setUser(studentService.getUserByBookedPlace(place));
        return placeDto;
    }

    public List<PlaceDto> entityToDto(List<Place> places) {
        return places.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Place dtoToEntity(PlaceDto placeDto) {
        Place place = new Place();
        place.setNumber(placeDto.getNumber());
        place.setPlaceStatus(placeDto.getPlaceStatus());
        return place;
    }

    public List<Place> dtoToEntity(List<PlaceDto> placeDtos) {
        return placeDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
