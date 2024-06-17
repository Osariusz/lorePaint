package org.osariusz.lorepaint.loreUserRole;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreRole.LoreRoleService;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoreUserRoleService {
    @Autowired
    private LoreUserRoleRepository loreUserRoleRepository;

    @Autowired
    private LoreRoleService loreRoleService;

    @Autowired
    private Validator validator;

    public void validateRole(LoreUserRole loreUserRole) {
        Validation.validate(loreUserRole, validator);
    }

    public boolean userHasLoreRole(Lore lore, User user, String role) {
        LoreRole loreRole = loreRoleService.getRoleByName(role);
        return loreUserRoleRepository.existsLoreUserRoleByLoreAndUserAndRole(lore, user, loreRole);
    }

    public LoreUserRole assignLoreUserRole(User user, Lore lore, String role) {
        if (userHasLoreRole(lore, user, role)) {
            throw new IllegalArgumentException("User already has that role");
        }
        LoreUserRole loreUserRole = new LoreUserRole();
        loreUserRole.setUser(user);
        loreUserRole.setLore(lore);
        loreUserRole.setRole(loreRoleService.getRoleByName(role));
        return loreUserRole;
    }

    public void saveRole(LoreUserRole loreUserRole) {
        validateRole(loreUserRole);
        loreUserRoleRepository.save(loreUserRole);
    }

}
