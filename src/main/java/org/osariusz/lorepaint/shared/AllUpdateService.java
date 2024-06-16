package org.osariusz.lorepaint.shared;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.lore.LoreService;
import org.osariusz.lorepaint.map.Map;
import org.osariusz.lorepaint.map.MapService;
import org.osariusz.lorepaint.mapUpdate.MapUpdateService;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.place.PlaceRepository;
import org.osariusz.lorepaint.place.PlaceService;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdateService;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserDTO;
import org.osariusz.lorepaint.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AllUpdateService {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private LoreService loreService;

    @Autowired
    private PlaceUpdateService placeUpdateService;

    @Autowired
    private MapService mapService;

    @Autowired
    private MapUpdateService mapUpdateService;

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private ModelMapper modelMapper;

    public Set<LocalDateTime> getAllUpdatesDates(long loreId, UserDTO userDTO) {
        Lore lore = loreService.getLoreById(loreId);
        if(lore == null) {
            throw new NoSuchElementException("Can't get all updates for null lore");
        }
        List<Update> updates = new ArrayList<>();

        // TODO: add map updates when maps will be configurable
        //updates.addAll(mapUpdateService.getAvailableUpdates(mapService.getAllMaps(lore), userDTO, loreId));
        updates.addAll(placeUpdateService.getAvailableUpdates(placeService.getAllPlaces(loreId), userDTO, loreId));

        Set<LocalDateTime> dates = new HashSet<>();
        updates.forEach(update -> {
            dates.add(update.getLore_date());
        });

        return dates;
    }
}
