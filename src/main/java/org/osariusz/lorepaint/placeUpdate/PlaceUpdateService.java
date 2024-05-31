package org.osariusz.lorepaint.placeUpdate;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
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

    public List<PlaceUpdate> getAllPlaceUpdatesUpTo(Place place, LocalDateTime localDateTime) {
        List<PlaceUpdate> allUpdates = getAllPlaceUpdates(place);
        return allUpdates.stream().filter(placeUpdate -> !placeUpdate.getLore_date().isAfter(localDateTime)).toList();
    }

    public PlaceUpdate getLastPlaceUpdate(Place place, LocalDateTime localDateTime) {
        return getAllPlaceUpdatesUpTo(place, localDateTime).stream().max((update1, update2) -> {
            if (update1.getLore_date().isEqual(update2.getLore_date())) {
                return update1.getCreated_at().compareTo(update2.getCreated_at());
            }
            return update1.getLore_date().compareTo(update2.getLore_date());
        }).orElseThrow(() -> new RuntimeException("No place update found"));
    }

    public List<PlaceUpdate> getAllPlaceUpdates(Place place) {
        return placeUpdateRepository.findAllByPlace(place);
    }
}
