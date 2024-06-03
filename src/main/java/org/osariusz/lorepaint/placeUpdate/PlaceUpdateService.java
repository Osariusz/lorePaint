package org.osariusz.lorepaint.placeUpdate;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.shared.UpdateService;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaceUpdateService extends UpdateService<Place, PlaceUpdate> {
    @Autowired
    private PlaceUpdateRepository placeUpdateRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private ModelMapper modelMapper;

    public void validatePlaceUpdate(PlaceUpdate placeUpdate) {
        Validation.validate(placeUpdate, validator);
    }

    public void saveFromPlaceUpdateDTO(Place place, PlaceUpdateDTO placeUpdateDTO) {
        PlaceUpdate newPlaceUpdate = modelMapper.map(placeUpdateDTO, PlaceUpdate.class);
        newPlaceUpdate.setPlace(place);
        newPlaceUpdate.setCreated_at(LocalDateTime.now());
        savePlaceUpdate(newPlaceUpdate);
    }

    public void savePlaceUpdate(PlaceUpdate placeUpdate) {
        validatePlaceUpdate(placeUpdate);
        placeUpdateRepository.save(placeUpdate);
    }

    public List<PlaceUpdate> getAllUpdates(Place place) {
        return placeUpdateRepository.findAllByPlace(place);
    }
}
