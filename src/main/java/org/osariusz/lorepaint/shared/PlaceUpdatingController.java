package org.osariusz.lorepaint.shared;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.place.PlaceService;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdateService;
import org.osariusz.lorepaint.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/place")
@PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) && @userRolesService.isMember(#lore, @userRolesService.springUserToDTO(principal))")
public class PlaceUpdatingController {
    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private PlaceUpdateService placeUpdateService;

    @Autowired
    private ModelMapper modelMapper;
    
    public void createSavePlace(PlaceCreateDTO placeCreateDTO) {
        Place place = modelMapper.map(placeCreateDTO, Place.class);
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRolesService.principalToUser(principal);
        place.setOwner(user);


        PlaceUpdate initialPlaceUpdate = modelMapper.map(placeCreateDTO, PlaceUpdate.class);
        initialPlaceUpdate.setPlace(place);
        place.setPlaceUpdates(new ArrayList<>(List.of(initialPlaceUpdate)));
        placeService.savePlaceWithUpdate(place, initialPlaceUpdate);
    }

    @PostMapping("/create")
    public void createPlace(@RequestBody PlaceCreateDTO placeCreateDTO) {
        createSavePlace(placeCreateDTO);
    }
}
