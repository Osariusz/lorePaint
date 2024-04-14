package org.osariusz.lorepaint.shared;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreRole.LoreRoleRepository;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleRepository;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserDTO;
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
    private LoreRoleRepository loreRoleRepository;

    @Autowired
    private LoreRepository loreRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO springUserToDTO(org.springframework.security.core.userdetails.User springUser) {
        User user = userRepository.findByUsername(springUser.getUsername()).orElseThrow();
        return modelMapper.map(user, UserDTO.class);
    }

    public List<LoreUserRole> getUserRoles(Lore lore, User user) {
        return loreUserRoleRepository.findAllByLoreAndUser(lore, user);
    }

    public boolean isAdmin(Lore lore, UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return getUserRoles(lore, user).stream().anyMatch((LoreUserRole loreUserRole) ->
                loreUserRole.getRole().equals(loreRoleRepository.findByRole(LoreRole.UserRole.GM)));
    }

    public boolean isMember(Lore lore, UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        List<LoreUserRole> loreUserRoles = loreUserRoleRepository.findAllByLoreAndUser(lore, user);
        loreUserRoles = loreUserRoles.stream().filter(userRole -> userRole.getRole().getRole().equals(LoreRole.UserRole.MEMBER)).toList();
        return !loreUserRoles.isEmpty();

    }

}
