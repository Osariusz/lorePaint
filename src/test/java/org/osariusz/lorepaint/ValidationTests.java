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
import org.osariusz.lorepaint.role.Role;
import org.osariusz.lorepaint.role.RoleService;
import org.osariusz.lorepaint.user.User;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


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

    @InjectMocks
    private LoreService loreService;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void LoreAssertDoestNotThrow() {

        // Arrange
        Lore lore = new Lore(); // Assuming Lore is a valid class. Populate it as necessary.
        lore.setName("a");
        lore.setMapUpdates(new ArrayList<>());
        lore.setPlaces(new ArrayList<>());
        lore.setRoles(new ArrayList<>());
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
        lore.setRoles(new ArrayList<>());
        // Act and Assert

        lore.getMapUpdates().add(new MapUpdate());
        assertThrows(ConstraintViolationException.class, () -> {loreService.validateLore(lore);});

        lore.setName("a");
        assertDoesNotThrow(() -> {loreService.validateLore(lore);});

    }

    @Test
    public void RoleAssertNameMustNotBeBlank() {
        Role role = new Role();
        role.setRole(null);
        role.setUser(new User());
        role.setLore(new Lore());
        assertThrows(ConstraintViolationException.class, () -> {roleService.validateRole(role);});
        role.setRole(Role.UserRole.ADMIN);
        assertDoesNotThrow(() -> {roleService.validateRole(role);});
    }

}
