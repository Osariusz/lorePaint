package org.osariusz.lorepaint.place;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.shared.PlaceCreateDTO;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private ModelMapper modelMapper;

    public void validatePlace(Place place) {
        Validation.validate(place, validator);
    }

    public void savePlace(Place place) {
        validatePlace(place);
        placeRepository.save(place);
    }

}
