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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/placeUpdate")
@PreAuthorize(
        "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) &&" +
                "@userRolesService.isMember(#place.lore, @userRolesService.principalToDTO(principal)) &&" +
                "@userRolesService.canSeePlace(#place, @userRolesService.principalToDTO(principal))"
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
    public ResponseEntity<PlaceUpdateDTO> getLastPlaceUpdate(@PathVariable("id") Place place, @RequestBody DateGetDTO placeDateGetDTO) {
        try {
            return new ResponseEntity<>(
                    modelMapper.map(
                            placeUpdateService.getLastUpdate(place, placeDateGetDTO.getLore_date()),
                            PlaceUpdateDTO.class
                    ),
                    HttpStatus.OK
            );
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
