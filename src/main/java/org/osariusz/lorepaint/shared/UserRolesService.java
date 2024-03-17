package org.osariusz.lorepaint.shared;

import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreRole.LoreRoleRepository;
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
    private LoreRoleRepository loreRoleRepository;

    @Autowired
    private LoreRepository loreRepository;

    public List<LoreRole> getUserRoles(long loreId, long userId) {
        Lore lore = loreRepository.getReferenceById(loreId);
        User user = userRepository.getReferenceById(userId);
        return loreRoleRepository.findAllByLoreAndUser(lore, user);
    }

    public boolean isAdmin(long loreId, long userId) {
        return getUserRoles(loreId, userId).stream().anyMatch((LoreRole loreRole) -> {
            return loreRole.getRole().equals(LoreRole.UserRole.GM);
        });
    }

}
