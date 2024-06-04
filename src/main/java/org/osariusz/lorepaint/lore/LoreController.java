package org.osariusz.lorepaint.lore;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.osariusz.lorepaint.shared.DateGetDTO;
import org.osariusz.lorepaint.shared.LoreMapService;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserDTO;
import org.osariusz.lorepaint.utils.RoleNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/lore")
@PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) && @userRolesService.isMember(#lore, @userRolesService.springUserToDTO(principal))")
public class LoreController {
    @Autowired
    private LoreService loreService;

    @Autowired
    private LoreMapService loreMapService;

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{id}")
    public Lore getLore(@PathVariable("id") long loreId) {
        return loreService.getLoreById(loreId);
    }

    @PostMapping("/{id}/get_last_map_update")
    public ResponseEntity<MapUpdate> getLastMapUpdate(@PathVariable("id") long loreId, @RequestBody DateGetDTO loreDateDTO) {
        Lore lore = loreService.getLoreById(loreId);
        try {
            return new ResponseEntity<>(loreMapService.getLastLoreMapUpdate(lore, loreDateDTO.getLore_date()), HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME)")
    public ResponseEntity<Lore> create(@RequestBody LoreCreateDTO loreCreateDTO, Principal principal) {
        Lore lore = loreService.createLore(loreCreateDTO, userRolesService.principalToUser(principal));
        return new ResponseEntity<>(lore, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/add_user")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) && @userRolesService.isGM(#lore, @userRolesService.springUserToDTO(principal))")
    public ResponseEntity<String> addUser(@PathVariable("id") long loreId, @RequestBody User user) {
        Lore lore = loreService.getLoreById(loreId);
        if(lore == null) {
            return new ResponseEntity<>("Lore not found", HttpStatus.BAD_REQUEST);
        }
        try {
            loreService.assignSaveLoreUserRole(user, lore, RoleNames.LORE_MEMBER_ROLE_NAME);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Can't add role for that user", HttpStatus.BAD_REQUEST);
        }

    }

    @MessageMapping("/{id}/set_mouse")
    //@SendTo("/{id}/get_mouse")
    @PreAuthorize("true") //custom authorization in method
    public ResponseEntity<String> mousePositions(@DestinationVariable("id") long lore, @Payload String message, Principal principal) {
        UserDTO userDTO = userRolesService.principalToDTO(principal);
        try {
            if(userRolesService.isUser(userDTO) && userRolesService.isMember(lore, userDTO)) {
                ObjectMapper objectMapper = new ObjectMapper();
                double[] coordinates = objectMapper.readValue(message, double[].class);
                MouseCursorDTO mouseCursorDTO = new MouseCursorDTO(principal.getName(), coordinates);
                messagingTemplate.convertAndSend(this.getClass().getAnnotation(RequestMapping.class).value()[0]+"/"+lore+"/get_mouse", mouseCursorDTO);
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/available")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME)")
    @PostFilter("@userRolesService.isMember(filterObject, @userRolesService.springUserToDTO(principal))")
    public List<Lore> getLores() {
        return loreService.getAllLores();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_ADMIN_ROLE_NAME)")
    public List<Lore> getAllLores() {
        return loreService.getAllLores();
    }
}
