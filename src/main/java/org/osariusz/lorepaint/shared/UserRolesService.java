package org.osariusz.lorepaint.shared;

import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleRepository;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRolesService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoreUserRoleRepository loreUserRoleRepository;

    @Autowired
    private LoreRepository loreRepository;

    public List<LoreUserRole> getUserRoles(long loreId, long userId) {
        Lore lore = loreRepository.getReferenceById(loreId);
        User user = userRepository.getReferenceById(userId);
        return loreUserRoleRepository.findAllByLoreAndUser(lore, user);
    }

    public boolean isAdmin(long loreId, long userId) {
        return getUserRoles(loreId, userId).stream().anyMatch((LoreUserRole loreUserRole) -> {
            return loreUserRole.getRole().equals(LoreUserRole.UserRole.GM);
        });
    }

}
