package org.osariusz.lorepaint.shared;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreService;
import org.osariusz.lorepaint.map.Map;
import org.osariusz.lorepaint.map.MapService;
import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.osariusz.lorepaint.mapUpdate.MapUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoreMapService {
    @Autowired
    private MapUpdateService mapUpdateService;

    @Autowired
    private MapService mapService;

    @Autowired
    private LoreService loreService;

    @Autowired
    private ModelMapper modelMapper;

    public Map getLoreMap(Lore lore) {
        return lore.getMap();
    }

    public MapUpdate getLastLoreMapUpdate(Lore lore, LocalDateTime localDateTime) {
        return mapUpdateService.getLastUpdate(getLoreMap(lore), localDateTime);
    }
}
