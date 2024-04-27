package org.osariusz.lorepaint.place;

import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdateRepository;
import org.osariusz.lorepaint.shared.PlaceCreateDTO;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PlaceUpdateRepository placeUpdateRepository;

    public void validatePlace(Place place) {
        Validation.validate(place, validator);
        int h = 0;
    }

    public void savePlace(Place place) {
        validatePlace(place);
        placeRepository.save(place);
    }

    public void savePlaceWithUpdate(Place place, PlaceUpdate placeUpdate) {

        placeRepository.save(place);
        //placeUpdateRepository.save(placeUpdate);
    }

}
