package org.osariusz.lorepaint;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.lore.LoreService;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreRole.LoreRoleRepository;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.map.Map;
import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.osariusz.lorepaint.mapUpdate.MapUpdateRepository;
import org.osariusz.lorepaint.mapUpdate.MapUpdateService;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleService;
import org.osariusz.lorepaint.user.User;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ValidationTests {

    @Spy
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private LoreRepository loreRepository;

    @Mock
    private MapUpdateRepository mapUpdateRepository;

    @InjectMocks
    private LoreService loreService;

    @InjectMocks
    private LoreUserRoleService roleService;

    @InjectMocks
    private MapUpdateService mapUpdateService;

    @Mock
    private LoreRoleRepository loreRoleRepository;

    public void memberRoleSetup() {
        LoreRole member = new LoreRole();
        member.setRole(LoreRole.UserRole.MEMBER);
        Mockito.when(loreRoleRepository.findByRole(LoreRole.UserRole.MEMBER)).thenReturn(member);
    }

    public void gmRoleSetup() {
        LoreRole gm = new LoreRole();
        gm.setRole(LoreRole.UserRole.GM);
        Mockito.when(loreRoleRepository.findByRole(LoreRole.UserRole.GM)).thenReturn(gm);
    }

    @Test
    public void LoreAssertDoestNotThrow() {

        // Arrange
        Lore lore = new Lore(); // Assuming Lore is a valid class. Populate it as necessary.
        Map map = new Map();
        lore.setMap(map);
        lore.setName("a");
        lore.setDescription("");
        map.setMapUpdates(new ArrayList<>());
        lore.setPlaces(new ArrayList<>());
        lore.setLoreUserRoles(new ArrayList<>());
        // Act and Assert

        map.getMapUpdates().add(new MapUpdate());
        assertDoesNotThrow(() -> {loreService.validateLore(lore);});
    }

    @Test
    public void LoreAssertThrowsMultipleProblems() {
        Lore lore = new Lore();
        lore.setName("a");
        assertThrows(ConstraintViolationException.class,() -> {loreService.validateLore(lore);});
    }

    @Test
    public void LoreAssertThrowsButNameChangeHelps() {

        // Arrange
        Lore lore = new Lore(); // Assuming Lore is a valid class. Populate it as necessary.
        Map map = new Map();
        lore.setMap(map);
        lore.setDescription("");

        map.setMapUpdates(new ArrayList<>());
        lore.setPlaces(new ArrayList<>());
        lore.setLoreUserRoles(new ArrayList<>());
        // Act and Assert

        map.getMapUpdates().add(new MapUpdate());
        assertThrows(ConstraintViolationException.class, () -> {loreService.validateLore(lore);});

        lore.setName("a");
        assertDoesNotThrow(() -> {loreService.validateLore(lore);});

    }

    @Test
    public void LoreRoleAssertNameMustNotBeBlank() {
        gmRoleSetup();

        LoreUserRole loreUserRole = new LoreUserRole();
        loreUserRole.setRole(null);
        loreUserRole.setUser(new User());
        loreUserRole.setLore(new Lore());
        assertThrows(ConstraintViolationException.class, () -> {roleService.validateRole(loreUserRole);});
        loreUserRole.setRole(loreRoleRepository.findByRole(LoreRole.UserRole.GM));
        assertDoesNotThrow(() -> {roleService.validateRole(loreUserRole);});
    }

    @Test
    public void MapUpdateLoreDateMustNotBeNull() {
        MapUpdate mapUpdate = new MapUpdate();
        mapUpdate.setX(0L);
        mapUpdate.setY(0L);
        assertThrows(ConstraintViolationException.class, () -> {mapUpdateService.validateMapUpdate(mapUpdate);});
        mapUpdate.setLore_date(LocalDateTime.of(2024,2,29,0,0));
        assertDoesNotThrow(() -> {mapUpdateService.validateMapUpdate(mapUpdate);});

    }

    @Test
    public void MapUpdateWrongDate() {
        MapUpdate mapUpdate = new MapUpdate();
        mapUpdate.setX(0L);
        mapUpdate.setY(0L);
        assertThrows(DateTimeException.class, () -> {mapUpdate.setLore_date(LocalDateTime.of(2024,2,31,0,0));});
    }

}
