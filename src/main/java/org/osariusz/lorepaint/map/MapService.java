package org.osariusz.lorepaint.map;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.osariusz.lorepaint.mapUpdate.MapUpdateService;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.place.PlaceRepository;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdateRepository;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {
    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private MapUpdateService mapUpdateService;

    @Autowired
    private Validator validator;

    @Autowired
    private ModelMapper modelMapper;

    public void validateMap(Map map) {
        Validation.validate(map, validator);
    }

    public Map createMap() {
        Map result = new Map();
        MapUpdate mapUpdate = new MapUpdate();
        //TODO: make below values configurable
        mapUpdate.setLore_date(LocalDateTime.now());
        mapUpdate.setX(0);
        mapUpdate.setY(0);

        result.setMapUpdates(new ArrayList<>(List.of(mapUpdate)));
        saveMap(result);
        return result;
    }

    public void saveMap(Map map) {
        validateMap(map);
        mapRepository.save(map);
    }

}