package org.osariusz.lorepaint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreRole.LoreRoleRepository;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleRepository;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserRepository;
import org.osariusz.lorepaint.utils.RoleNames;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserLoreLoreUserRoleServiceTest {

    @Mock
    private LoreRepository loreRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoreUserRoleRepository loreUserRoleRepository;

    @Spy
    private LoreRoleRepository loreRoleRepository;

    @InjectMocks
    private UserRolesService userRolesService;

    @Test
    public void GetRolesTest() {
        User user = new User();
        user.setUsername("1");
        Lore lore = new Lore();
        lore.setId(1L);

        LoreRole member = new LoreRole();
        member.setRole(RoleNames.LORE_MEMBER_ROLE_NAME);

        LoreUserRole loreUserRole = new LoreUserRole();
        loreUserRole.setRole(member);
        loreUserRole.setLore(lore);
        loreUserRole.setUser(user);
        Mockito.when(loreUserRoleRepository.findAllByLoreAndUser(Mockito.any(), Mockito.any())).thenReturn(List.of(loreUserRole));

        List<LoreUserRole> loreUserRoles = userRolesService.getUserLoreRoles(lore, user);
        assert loreUserRoles.size() == 1;
        assert loreUserRoles.get(0).getRole().getRole().equals(RoleNames.LORE_MEMBER_ROLE_NAME);

    }

}
