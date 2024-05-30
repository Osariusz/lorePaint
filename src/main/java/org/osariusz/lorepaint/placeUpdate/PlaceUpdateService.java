package org.osariusz.lorepaint.placeUpdate;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaceUpdateService {
    @Autowired
    private PlaceUpdateRepository placeUpdateRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private ModelMapper modelMapper;

    public void validatePlaceUpdate(PlaceUpdate placeUpdate) {
        Validation.validate(placeUpdate, validator);
    }

    public void saveFromPlaceUpdateDTO(PlaceUpdateDTO placeUpdateDTO) {
        PlaceUpdate newPlaceUpdate = modelMapper.map(placeUpdateDTO, PlaceUpdate.class);
        savePlaceUpdate(newPlaceUpdate);
    }

    public void savePlaceUpdate(PlaceUpdate placeUpdate) {
        validatePlaceUpdate(placeUpdate);
        placeUpdateRepository.save(placeUpdate);
    }

    public List<PlaceUpdate> getAllPlaceUpdatesBefore(Place place, LocalDateTime localDateTime) {
        List<PlaceUpdate> allUpdates = getAllPlaceUpdates(place);
        return allUpdates.stream().filter(placeUpdate -> !placeUpdate.getLore_date().isAfter(localDateTime)).toList();
    }

    public List<PlaceUpdate> getAllPlaceUpdates(Place place) {
        return placeUpdateRepository.findAllByPlace(place);
    }
}
