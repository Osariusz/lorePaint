package org.osariusz.lorepaint.lore;

import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.utils.RoleNames;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/lore")
@PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) && @userRolesService.isMember(#lore, @userRolesService.springUserToDTO(principal))")
public class LoreController {
    @Autowired
    private LoreService loreService;

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{id}")
    Lore getLore(@PathVariable("id") Lore lore) {
        return lore;
    }

    @MessageMapping("/{id}/set_mouse")
    @SendTo("/{id}/get_mouse")
    @PreAuthorize("@userRolesService.isMember(#lore, @userRolesService.springUserToDTO(principal))")
    //@PreAuthorize("true")
    public String mousePositions(@DestinationVariable("id") long lore, @Payload String message, Principal principal) throws Exception {
        //messagingTemplate.convertAndSend("/api/topic/reply", (message));
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return message;
    }

    @GetMapping("/available")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME)")
    @PostFilter("@userRolesService.isMember(filterObject, @userRolesService.springUserToDTO(principal))")
    List<Lore> getLores() {
        return loreService.getAllLores();
    }
}
