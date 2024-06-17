package org.osariusz.lorepaint.shared;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.shared.AllUpdateService;
import org.osariusz.lorepaint.shared.DateGetDTO;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/api/allUpdate")
@PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) &&" +
        " @userRolesService.isMember(@loreService.getLoreById(#loreId), @userRolesService.springUserToDTO(principal))")
public class AllUpdateController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AllUpdateService allUpdateService;
    @Autowired
    private UserRolesService userRolesService;

    @GetMapping("/{id}")
    public ResponseEntity<Set<LocalDateTime>> getLastPlaceUpdate(@PathVariable("id") long loreId, Principal principal) {
        try {
            return new ResponseEntity<>(
                    allUpdateService.getAllUpdatesDates(loreId, userRolesService.principalToDTO(principal)),
                    HttpStatus.OK
            );
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
