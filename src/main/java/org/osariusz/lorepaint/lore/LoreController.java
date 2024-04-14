package org.osariusz.lorepaint.lore;

import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.utils.RoleNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lore")
@PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME) && @userRolesService.isMember(#lore, @userRolesService.springUserToDTO(principal))")
public class LoreController {
    @Autowired
    private LoreService loreService;

    @Autowired
    private UserRolesService userRolesService;

    @GetMapping("/{id}")
    Lore getLore(@PathVariable("id") Lore lore) {
        return lore;
    }

    @GetMapping("/available")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME)")
    @PostFilter("@userRolesService.isMember(filterObject, @userRolesService.springUserToDTO(principal))")
    List<Lore> getLores() {
        return loreService.getAllLores();
    }

}
