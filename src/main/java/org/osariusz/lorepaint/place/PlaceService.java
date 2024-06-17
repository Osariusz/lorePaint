package org.osariusz.lorepaint.place;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreService;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdateService;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    private LoreService loreService;

    public void validatePlace(Place place) {
        Validation.validate(place, validator);
    }

    public void savePlace(Place place) {
        place.setLast_edit(LocalDateTime.now());
        validatePlace(place);
        placeRepository.save(place);
    }

    public Place getPlaceById(Long id) {
        Place place = placeRepository.findByIdAndRemovedAtIsNull(id).orElse(null);
        if (place == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
        return place;
    }


    public List<Place> getAllPlaces() {
        return placeRepository.findAllByRemovedAtIsNull();
    }

    public List<Place> getAllPlaces(long loreId) {
        Lore lore = loreService.getLoreById(loreId);
        if (lore == null) {
            throw new RuntimeException("Lore not found");
        }
        return placeRepository.findAllByLoreAndRemovedAtIsNull(lore);
    }

    public boolean placeCreatedBefore(Place place, LocalDateTime localDateTime) {
        return !placeUpdateService.getAllUpdatesUpTo(place, localDateTime).isEmpty();
    }

    public List<Place> getAllPlacesCreatedBefore(long loreId, LocalDateTime localDateTime) {
        List<Place> places = getAllPlaces(loreId);
        return places.stream().filter(place -> placeCreatedBefore(place, localDateTime)).collect(Collectors.toList());
    }
}
