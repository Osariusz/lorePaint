package org.osariusz.lorepaint;

import jakarta.validation.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.lore.LoreService;
import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.osariusz.lorepaint.mapUpdate.MapUpdateRepository;
import org.osariusz.lorepaint.mapUpdate.MapUpdateService;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreRole.LoreRoleService;
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
    private LoreRoleService roleService;

    @InjectMocks
    private MapUpdateService mapUpdateService;

    @Test
    public void LoreAssertDoestNotThrow() {

        // Arrange
        Lore lore = new Lore(); // Assuming Lore is a valid class. Populate it as necessary.
        lore.setName("a");
        lore.setMapUpdates(new ArrayList<>());
        lore.setPlaces(new ArrayList<>());
        lore.setLoreRoles(new ArrayList<>());
        // Act and Assert

        lore.getMapUpdates().add(new MapUpdate());
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

        lore.setMapUpdates(new ArrayList<>());
        lore.setPlaces(new ArrayList<>());
        lore.setLoreRoles(new ArrayList<>());
        // Act and Assert

        lore.getMapUpdates().add(new MapUpdate());
        assertThrows(ConstraintViolationException.class, () -> {loreService.validateLore(lore);});

        lore.setName("a");
        assertDoesNotThrow(() -> {loreService.validateLore(lore);});

    }

    @Test
    public void LoreRoleAssertNameMustNotBeBlank() {
        LoreRole loreRole = new LoreRole();
        loreRole.setRole(null);
        loreRole.setUser(new User());
        loreRole.setLore(new Lore());
        assertThrows(ConstraintViolationException.class, () -> {roleService.validateRole(loreRole);});
        loreRole.setRole(LoreRole.UserRole.GM);
        assertDoesNotThrow(() -> {roleService.validateRole(loreRole);});
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
