package org.osariusz.lorepaint.placeUpdate;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.shared.Update;
import org.osariusz.lorepaint.shared.UpdateService;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.user.UserDTO;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceUpdateService extends UpdateService<Place, PlaceUpdate> {
    @Autowired
    private PlaceUpdateRepository placeUpdateRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRolesService userRolesService;

    public void validatePlaceUpdate(PlaceUpdate placeUpdate) {
        Validation.validate(placeUpdate, validator);
    }

    public void saveFromPlaceUpdateDTO(Place place, PlaceUpdateDTO placeUpdateDTO) {
        PlaceUpdate newPlaceUpdate = modelMapper.map(placeUpdateDTO, PlaceUpdate.class);
        newPlaceUpdate.setId(null);
        newPlaceUpdate.setPlace(place);
        saveNewPlaceUpdate(newPlaceUpdate);
    }

    public void fixNexPlaceUpdate(Place place, PlaceUpdate placeUpdate) {
        placeUpdate.setId(null);
        placeUpdate.setPlace(place);
        placeUpdate.setCreated_at(LocalDateTime.now());
    }

    public void saveNewPlaceUpdate(PlaceUpdate placeUpdate) {
        placeUpdate.setCreated_at(LocalDateTime.now());
        savePlaceUpdate(placeUpdate);
    }

    public void savePlaceUpdate(PlaceUpdate placeUpdate) {
        validatePlaceUpdate(placeUpdate);
        placeUpdateRepository.save(placeUpdate);
    }

    public List<PlaceUpdate> getAllUpdates(Place place) {
        return placeUpdateRepository.findAllByPlace(place);
    }

    @Override
    public boolean objectAccessFunction(Place updatedObject, UserDTO userDTO) {
        return userRolesService.canSeePlace(updatedObject, userDTO);
    }

}
