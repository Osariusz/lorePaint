package org.osariusz.lorepaint.mapUpdate;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.map.Map;
import org.osariusz.lorepaint.shared.UpdateService;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MapUpdateService extends UpdateService<Map, MapUpdate> {
    @Autowired
    private MapUpdateRepository mapUpdateRepository;

    @Autowired
    private Validator validator;

    public void validateMapUpdate(MapUpdate mapUpdate) {
        Validation.validate(mapUpdate, validator);
    }

    public void saveMapUpdate(MapUpdate mapUpdate) {
        validateMapUpdate(mapUpdate);
        mapUpdateRepository.save(mapUpdate);
    }

    @Override
    public List<MapUpdate> getAllUpdates(Map updatedObject) {
        return mapUpdateRepository.findAllByMap(updatedObject);
    }

    public MapUpdate createMapUpdate(Map map, String mapPath) {
        MapUpdate mapUpdate = new MapUpdate();
        mapUpdate.setPicture_path(mapPath);
        mapUpdate.setCreated_at(LocalDateTime.now());
        //TODO: make below values configurable
        mapUpdate.setLore_date(LocalDateTime.of(1,1,1,0,0));
        mapUpdate.setX(0);
        mapUpdate.setY(0);
        mapUpdate.setMap(map);
        //saveMapUpdate(mapUpdate);
        return mapUpdate;
    }
}
