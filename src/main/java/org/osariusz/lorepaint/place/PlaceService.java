package org.osariusz.lorepaint.place;

import org.osariusz.lorepaint.lore.LoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;
}
