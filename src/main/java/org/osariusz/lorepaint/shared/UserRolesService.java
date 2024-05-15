package org.osariusz.lorepaint.shared;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.SystemRole.SystemRole;
import org.osariusz.lorepaint.SystemRole.SystemRoleRepository;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.loreRole.LoreRoleRepository;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleRepository;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.osariusz.lorepaint.systemUserRole.SystemUserRoleRepository;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserDTO;
import org.osariusz.lorepaint.user.UserRepository;
import org.osariusz.lorepaint.utils.RoleNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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

    @Autowired
    private UserDetailsService userDetailsService;

    public UserDTO springUserToDTO(org.springframework.security.core.userdetails.User springUser) {
        User user = userRepository.findByUsername(springUser.getUsername()).orElseThrow();
        return modelMapper.map(user, UserDTO.class);
    }

    public User principalToUser(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        return modelMapper.map(user, User.class);
    }

    public UserDTO principalToDTO(Principal principal) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(principal.getName());
        return userDTO;
    }

    public List<LoreUserRole> getUserLoreRoles(Lore lore, User user) {
        return loreUserRoleRepository.findAllByLoreAndUser(lore, user);
    }

    public List<? extends GrantedAuthority> getUserSystemRoles(UserDTO user) {
        return userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities().stream().toList();
    }

    public boolean isUser(UserDTO userDTO) {
        return getUserSystemRoles(userDTO).stream().anyMatch((authority) ->
                authority.getAuthority().equals(RoleNames.SYSTEM_USER_ROLE_NAME)
        );
    }

    public boolean isAdmin(UserDTO userDTO) {
        return getUserSystemRoles(userDTO).stream().anyMatch((authority) ->
            authority.getAuthority().equals(RoleNames.SYSTEM_ADMIN_ROLE_NAME)
        );
    }

    public boolean isGM(Lore lore, UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return getUserLoreRoles(lore, user).stream().anyMatch((LoreUserRole loreUserRole) ->
                loreUserRole.getRole().equals(loreRoleRepository.findByRole(RoleNames.LORE_GM_ROLE_NAME)));
    }

    public boolean isMember(long loreId, UserDTO userDTO) {
        Lore lore = loreRepository.findById(loreId);
        return isMember(lore, userDTO);
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

    public boolean canSeePlace(Place place, UserDTO userDTO) {
        boolean t = (isMember(place.getLore(), userDTO) && !place.getIsSecret()) || canModifyPlace(place, userDTO);;
        if(!t) {
            int h = 0;
        }
        return t;
    }
    public boolean canModifyPlace(Place place, UserDTO userDTO) {
        return
                (Objects.equals(place.getOwner().getUsername(), userDTO.getUsername()) && isMember(place.getLore(), userDTO)) ||
                        isGM(place.getLore(), userDTO) ||
                        isAdmin(userDTO);
    }

}
