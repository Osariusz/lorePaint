package org.osariusz.lorepaint.mapUpdate;

import org.osariusz.lorepaint.lore.LoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapUpdateService {
    @Autowired
    private MapUpdateRepository mapUpdateRepository;
}
