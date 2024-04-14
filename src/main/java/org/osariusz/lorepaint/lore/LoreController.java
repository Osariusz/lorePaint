package org.osariusz.lorepaint.lore;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleService;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lore")
public class LoreController {
    @Autowired
    private LoreService loreService;

    @Autowired
    private UserRolesService userRolesService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') && @userRolesService.isMember(#lore, @userRolesService.springUserToDTO(principal))")
    Lore getLore(@PathVariable("id") Lore lore) {
        return lore;
    }

}
