package org.osariusz.lorepaint.mapUpdate;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapUpdateService {
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
}
