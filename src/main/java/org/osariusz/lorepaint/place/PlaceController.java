package org.osariusz.lorepaint.place;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreService;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/place")
@PreAuthorize(
        "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) && " +
        "@userRolesService.isMember(#lore, @userRolesService.springUserToDTO(principal))"
)
public class PlaceController {
    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private PlaceService placeService;

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
        placeService.savePlace(place);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPlace(@RequestBody PlaceCreateDTO placeCreateDTO) {
        createSavePlace(placeCreateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME)")
    @PostFilter("@userRolesService.canSeePlace(filterObject, @userRolesService.springUserToDTO(principal))")
    public List<Place> getLores(@PathVariable("id") Lore lore) {
        return placeService.getAllPlaces(lore);
    }
}