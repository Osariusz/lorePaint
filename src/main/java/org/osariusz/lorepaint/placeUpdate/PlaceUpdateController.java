package org.osariusz.lorepaint.placeUpdate;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.shared.DateGetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/placeUpdate")
@PreAuthorize(
                "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) &&"+
                "@userRolesService.isMember(#place.lore, @userRolesService.principalToDTO(@principal)) &&"+
                "@userRolesService.canSeePlace(#place, @userRolesService.principalToDTO(@principal))"
)
public class PlaceUpdateController {

    @Autowired
    private PlaceUpdateService placeUpdateService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/{id}/create")
    @PreAuthorize(
                    "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) &&" +
                    "@userRolesService.canModifyPlace(#place, @userRolesService.springUserToDTO(principal))"
    )
    public ResponseEntity<String> updatePlace(@PathVariable("id") Place place, @RequestBody PlaceUpdateDTO placeUpdateDTO) {
        placeUpdateService.saveFromPlaceUpdateDTO(place, placeUpdateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/last/{id}")
    @PreAuthorize(
            "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) &&" +
            "@userRolesService.canSeePlace(#place, @userRolesService.springUserToDTO(principal))"
    )
    public PlaceUpdateDTO getLastPlaceUpdate(@PathVariable("id") Place place, @RequestBody DateGetDTO placeDateGetDTO) {
        return modelMapper.map(placeUpdateService.getLastPlaceUpdate(place, placeDateGetDTO.getDate()), PlaceUpdateDTO.class);
    }

    @GetMapping("/{place}/allUpdates")
    public List<PlaceUpdate> getAllPlaceUpdates(@PathVariable("id") Place place) {
        return placeUpdateService.getAllPlaceUpdates(place);
    }
}
