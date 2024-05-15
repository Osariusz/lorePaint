package org.osariusz.lorepaint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreRole.LoreRoleRepository;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.place.PlaceRepository;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleRepository;
import org.osariusz.lorepaint.shared.UserMapService;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserDTO;
import org.osariusz.lorepaint.user.UserRepository;
import org.osariusz.lorepaint.utils.RoleNames;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Collections;
import java.util.List;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserMapServiceTest {

    @Mock
    private LoreRepository loreRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoreUserRoleRepository loreUserRoleRepository;

    @Spy
    @InjectMocks
    private UserRolesService userRolesService;

    @InjectMocks
    private UserMapService userMapService;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private LoreRoleRepository loreRoleRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private User user1;
    private User user2;
    private User user3;
    private Lore lore;
    private LoreUserRole loreUserRole1;
    private LoreUserRole loreUserRole2;
    private Place secretPlace;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        LoreRole member = new LoreRole();
        member.setRole(RoleNames.LORE_MEMBER_ROLE_NAME);

        LoreRole gm = new LoreRole();
        gm.setRole(RoleNames.LORE_GM_ROLE_NAME);

        Mockito.when(loreRoleRepository.findByRole(RoleNames.LORE_MEMBER_ROLE_NAME)).thenReturn(member);
        Mockito.when(loreRoleRepository.findByRole(RoleNames.LORE_GM_ROLE_NAME)).thenReturn(gm);

        user1 = new User();
        user1.setId(1L);
        user2 = new User();
        user2.setId(2L);
        user3 = new User();
        user3.setId(3L);

        lore = new Lore();
        lore.setId(1L);

        loreUserRole1 = new LoreUserRole();
        loreUserRole1.setRole(loreRoleRepository.findByRole(RoleNames.LORE_MEMBER_ROLE_NAME));
        loreUserRole1.setLore(lore);
        loreUserRole1.setUser(user1);

        loreUserRole2 = new LoreUserRole();
        loreUserRole2.setRole(loreRoleRepository.findByRole(RoleNames.LORE_GM_ROLE_NAME));
        loreUserRole2.setLore(lore);
        loreUserRole2.setUser(user2);

        secretPlace = new Place();
        secretPlace.setIsSecret(true);
        secretPlace.setLore(lore);
        secretPlace.setOwner(user3);

        Mockito.when(userRepository.getReferenceById(user1.getId())).thenReturn(user1);
        Mockito.when(userRepository.getReferenceById(user2.getId())).thenReturn(user2);
        Mockito.when(loreRepository.getReferenceById(lore.getId())).thenReturn(lore);
        Mockito.when(placeRepository.findAllByLore(Mockito.any())).thenReturn(List.of(secretPlace));
    }

    @Test
    public void NoVisiblePlaces() {
        Mockito.when(loreUserRoleRepository.findAllByLoreAndUser(Mockito.any(), Mockito.any())).thenAnswer(invocation -> {
            User user = (User) invocation.getArguments()[1];
            if (user.getId().equals(user1.getId())) {
                return List.of(loreUserRole1);
            } else if (user.getId().equals(user2.getId())) {
                return List.of(loreUserRole2);
            }
            return Collections.emptyList();
        });

        List<Place> user1Places = userMapService.getAllAccessiblePlaces(lore, modelMapper.map(user1, UserDTO.class));
        List<Place> user2Places = userMapService.getAllAccessiblePlaces(lore, modelMapper.map(user2, UserDTO.class));

        assert user1Places.isEmpty();
        assert !user2Places.isEmpty();
    }
}
