package org.osariusz.lorepaint.placeUpdate;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceUpdateService {
    @Autowired
    private PlaceUpdateRepository placeUpdateRepository;

    @Autowired
    private Validator validator;

    public void validatePlaceUpdate(PlaceUpdate placeUpdate) {
        Validation.validate(placeUpdate, validator);
    }

    public void savePlaceUpdate(PlaceUpdate placeUpdate) {
        validatePlaceUpdate(placeUpdate);
        placeUpdateRepository.save(placeUpdate);
    }
}
