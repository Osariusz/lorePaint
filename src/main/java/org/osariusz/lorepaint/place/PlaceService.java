package org.osariusz.lorepaint.place;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private Validator validator;

    public void validatePlace(Place place) {
        Validation.validate(place, validator);
    }

    public void savePlace(Place place) {
        validatePlace(place);
        placeRepository.save(place);
    }

}
