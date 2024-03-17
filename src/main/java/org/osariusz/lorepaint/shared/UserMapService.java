package org.osariusz.lorepaint.shared;

import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.place.PlaceRepository;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoreRepository loreRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRolesService userRolesService;

    public List<Place> getAllAccessiblePlaces(long loreId, long userId) {
        Lore lore = loreRepository.getReferenceById(loreId);
        User user = userRepository.getReferenceById(userId);
        List<Place> lorePlaces = placeRepository.getAllByLore(lore);
        return lorePlaces.stream().filter((Place place) ->
                !place.getIsSecret() ||
                userRolesService.isAdmin(loreId, userId) ||
                place.getOwner().equals(user)).toList();
        }
}
