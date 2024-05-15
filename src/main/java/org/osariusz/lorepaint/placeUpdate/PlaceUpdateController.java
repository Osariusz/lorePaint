package org.osariusz.lorepaint.placeUpdate;

import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.place.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/placeUpdate")
@PreAuthorize(
                "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) &&"+
                "@userRolesService.isMember(#place.lore, @userRolesService.principalToDTO(@principal)) &&"+
                "@userRolesService.canSeePlace(#place, @userRolesService.principalToDTO(@principal))"
)
public class PlaceUpdateController {

    @Autowired
    private PlaceUpdateService placeUpdateService;

    @PostMapping("/create")
    @PreAuthorize(
                    "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) &&" +
                    "@userRolesService.canModifyPlace(#placeUpdateDTO.place, @userRolesService.springUserToDTO(principal))"
    )
    public ResponseEntity<String> updatePlace(@RequestBody PlaceUpdateDTO placeUpdateDTO) {
        placeUpdateService.saveFromPlaceUpdateDTO(placeUpdateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{place}/allUpdates")
    public List<PlaceUpdate> getAllPlaceUpdates(@PathVariable("id") Place place) {
        return placeUpdateService.getAllPlaceUpdates(place);
    }
}
