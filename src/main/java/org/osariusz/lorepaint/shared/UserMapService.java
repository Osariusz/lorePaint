package org.osariusz.lorepaint.shared;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.place.PlaceRepository;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserDTO;
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

    @Autowired
    private ModelMapper modelMapper;

    public List<Place> getAllAccessiblePlaces(Lore lore, UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        List<Place> lorePlaces = placeRepository.findAllByLoreAndRemovedAtIsNull(lore);
        return lorePlaces.stream().filter((Place place) ->
                !place.getIsSecret() ||
                userRolesService.isGM(lore, userDTO) ||
                place.getOwner().equals(user)).toList();
        }
}
