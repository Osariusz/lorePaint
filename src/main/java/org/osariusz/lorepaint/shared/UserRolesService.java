package org.osariusz.lorepaint.shared;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.SystemRole.SystemRoleRepository;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.loreRole.LoreRoleRepository;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleRepository;
import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.osariusz.lorepaint.systemUserRole.SystemUserRoleRepository;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserDTO;
import org.osariusz.lorepaint.user.UserRepository;
import org.osariusz.lorepaint.utils.RoleNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserRolesService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SystemUserRoleRepository systemUserRoleRepository;

    @Autowired
    private SystemRoleRepository systemRoleRepository;

    @Autowired
    private LoreUserRoleRepository loreUserRoleRepository;

    @Autowired
    private LoreRoleRepository loreRoleRepository;

    @Autowired
    private LoreRepository loreRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO springUserToDTO(org.springframework.security.core.userdetails.User springUser) {
        User user = userRepository.findByUsername(springUser.getUsername()).orElseThrow();
        return modelMapper.map(user, UserDTO.class);
    }

    public User principalToUser(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        return modelMapper.map(user, User.class);
    }

    public List<LoreUserRole> getUserLoreRoles(Lore lore, User user) {
        return loreUserRoleRepository.findAllByLoreAndUser(lore, user);
    }

    public List<SystemUserRole> getUserSystemRoles(User user) {
        return systemUserRoleRepository.findAllByUser(user);
    }


    public boolean isAdmin(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return getUserSystemRoles(user).stream().anyMatch((SystemUserRole systemUserRole) ->
            systemUserRole.getRole().equals(systemRoleRepository.findByRole(RoleNames.SYSTEM_ADMIN_ROLE_NAME))
        );
    }

    public boolean isGM(Lore lore, UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return getUserLoreRoles(lore, user).stream().anyMatch((LoreUserRole loreUserRole) ->
                loreUserRole.getRole().equals(loreRoleRepository.findByRole(RoleNames.LORE_GM_ROLE_NAME)));
    }

    public boolean isMember(Lore lore, UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if(isAdmin(userDTO)) {
            return true;
        }
        List<LoreUserRole> loreUserRoles = loreUserRoleRepository.findAllByLoreAndUser(lore, user);
        loreUserRoles = loreUserRoles.stream().filter(userRole -> userRole.getRole().getRole().equals(RoleNames.LORE_MEMBER_ROLE_NAME)).toList();
        return !loreUserRoles.isEmpty();

    }

}
