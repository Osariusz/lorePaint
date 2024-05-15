package org.osariusz.lorepaint.place;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdateRepository;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    }

    public void savePlace(Place place) {
        validatePlace(place);
        placeRepository.save(place);
    }

    public List<Place> getAllPlaces(Lore lore) {
        return placeRepository.findAllByLore(lore);
    }
}
