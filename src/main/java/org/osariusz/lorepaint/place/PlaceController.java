package org.osariusz.lorepaint.place;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdateService;
import org.osariusz.lorepaint.shared.DateGetDTO;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/place")
@PreAuthorize(
        "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) && " +
                "@userRolesService.isMember(@loreService.getLoreById(#loreId), @userRolesService.springUserToDTO(principal))"
)
public class PlaceController {
    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    @PreAuthorize(
            "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) && " +
                    "@userRolesService.isMember(#placeCreateDTO.loreId, @userRolesService.springUserToDTO(principal))"
    )
    public ResponseEntity<String> createPlace(@RequestBody PlaceCreateDTO placeCreateDTO, Principal principal) {
        placeService.createSavePlace(placeCreateDTO, userRolesService.principalToUser(principal));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize(
            "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) && " +
                    "@userRolesService.canSeePlace(@placeService.getPlaceById(#placeId), @userRolesService.springUserToDTO(principal))"
    )
    public Place getPlace(@PathVariable("id") long placeId) {
        return placeService.getPlaceById(placeId);
    }

    @GetMapping("/all/{id}")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_ADMIN_ROLE_NAME)")
    public List<Place> getAll(@PathVariable("id") long loreId) {
        return placeService.getAllPlaces(loreId);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_ADMIN_ROLE_NAME)")
    public List<Place> getAll() {
        return placeService.getAllPlaces();
    }

    @PostMapping("/all_before/{id}")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME)")
    @PostFilter("@userRolesService.canSeePlace(filterObject, @userRolesService.springUserToDTO(principal))")
    public List<Place> getAllPlacesForDate(@PathVariable("id") long loreId, @Valid @RequestBody DateGetDTO dateInfo) {
        return placeService.getAllPlacesCreatedBefore(loreId, dateInfo.getLore_date());
    }
}