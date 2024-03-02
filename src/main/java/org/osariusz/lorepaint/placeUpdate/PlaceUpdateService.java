package org.osariusz.lorepaint.placeUpdate;

import org.osariusz.lorepaint.lore.LoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceUpdateService {
    @Autowired
    private PlaceUpdateRepository placeUpdateRepository;
}
