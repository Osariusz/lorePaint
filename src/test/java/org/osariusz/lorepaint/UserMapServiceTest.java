package org.osariusz.lorepaint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.place.PlaceRepository;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreRole.LoreRoleRepository;
import org.osariusz.lorepaint.shared.UserMapService;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserRepository;
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
    private LoreRoleRepository loreRoleRepository;

    @Spy
    @InjectMocks
    private UserRolesService userRolesService;

    @InjectMocks
    private UserMapService userMapService;

    @Mock
    private PlaceRepository placeRepository;

    private User user1;
    private User user2;
    private User user3;
    private Lore lore;
    private LoreRole loreRole1;
    private LoreRole loreRole2;
    private Place secretPlace;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);


        user1 = new User();
        user1.setId(1L);
        user2 = new User();
        user2.setId(2L);
        user3 = new User();
        user3.setId(3L);

        lore = new Lore();
        lore.setId(1L);

        loreRole1 = new LoreRole();
        loreRole1.setRole(LoreRole.UserRole.MEMBER);
        loreRole1.setLore(lore);
        loreRole1.setUser(user1);

        loreRole2 = new LoreRole();
        loreRole2.setRole(LoreRole.UserRole.GM);
        loreRole2.setLore(lore);
        loreRole2.setUser(user2);

        secretPlace = new Place();
        secretPlace.setIsSecret(true);
        secretPlace.setLore(lore);
        secretPlace.setOwner(user3);

        Mockito.when(userRepository.getReferenceById(user1.getId())).thenReturn(user1);
        Mockito.when(userRepository.getReferenceById(user2.getId())).thenReturn(user2);
        Mockito.when(loreRepository.getReferenceById(lore.getId())).thenReturn(lore);
        Mockito.when(placeRepository.getAllByLore(Mockito.any())).thenReturn(List.of(secretPlace));
    }

    @Test
    public void NoVisiblePlaces() {
        Mockito.when(loreRoleRepository.findAllByLoreAndUser(Mockito.any(), Mockito.any())).thenAnswer(invocation -> {
            User user = (User) invocation.getArguments()[1];
            if (user.getId().equals(user1.getId())) {
                return List.of(loreRole1);
            } else if (user.getId().equals(user2.getId())) {
                return List.of(loreRole2);
            }
            return Collections.emptyList();
        });

        List<LoreRole> e = userRolesService.getUserRoles(lore.getId(), user1.getId());

        List<Place> user1Places = userMapService.getAllAccessiblePlaces(lore.getId(), user1.getId());
        List<Place> user2Places = userMapService.getAllAccessiblePlaces(lore.getId(), user2.getId());

        assert user1Places.isEmpty();
        assert !user2Places.isEmpty();
    }
}
