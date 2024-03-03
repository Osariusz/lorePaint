package org.osariusz.lorepaint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.osariusz.lorepaint.role.Role;
import org.osariusz.lorepaint.role.RoleRepository;
import org.osariusz.lorepaint.role.RoleService;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserRepository;
import org.osariusz.lorepaint.user.UserService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    @Mock
    private LoreRepository loreRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserRolesService userRolesService;

    @Test
    public void GetRolesTest() {
        User user = new User();
        user.setId(1L);
        Lore lore = new Lore();
        lore.setId(1L);



        Role role = new Role();
        role.setRole(Role.UserRole.USER);
        role.setLore(lore);
        role.setUser(user);
        Mockito.when(roleRepository.findAllByLoreAndUser(Mockito.any(), Mockito.any())).thenReturn(List.of(role));

        List<Role> roles = userRolesService.getUserRoles(lore.getId(), user.getId());
        assert roles.size() == 1;
        assert roles.get(0).getRole().equals(Role.UserRole.USER);

    }

}
