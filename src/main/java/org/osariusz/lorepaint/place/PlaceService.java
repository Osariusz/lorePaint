package org.osariusz.lorepaint.place;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdateRepository;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdateService;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceUpdateService placeUpdateService;

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

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public List<Place> getAllPlaces(Lore lore) {
        return placeRepository.findAllByLore(lore);
    }

    public boolean placeCreatedBefore(Place place, LocalDateTime localDateTime) {
        return !placeUpdateService.getAllPlaceUpdatesBefore(place, localDateTime).isEmpty();
    }

    public List<Place> getAllPlacesCreatedBefore(Lore lore, LocalDateTime localDateTime) {
        List<Place> places = getAllPlaces(lore);
        return places.stream().filter(place -> placeCreatedBefore(place, localDateTime)).collect(Collectors.toList());
    }
}
