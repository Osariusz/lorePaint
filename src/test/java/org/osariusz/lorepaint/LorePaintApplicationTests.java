package org.osariusz.lorepaint;

import jakarta.validation.*;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Hibernate;
import org.hibernate.validator.internal.engine.ValidatorImpl;
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
import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class LorePaintApplicationTests {

    @Spy
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private LoreRepository loreRepository;

    @InjectMocks
    private LoreService loreService;

    @Test
    public void validate_doesNotThrowException() {

        // Arrange
        Lore lore = new Lore(); // Assuming Lore is a valid class. Populate it as necessary.
        lore.setName("a");
        lore.setMapUpdates(new ArrayList<>());
        lore.setPlaces(new ArrayList<>());
        lore.setRoles(new ArrayList<>());
        // Act and Assert

        lore.getMapUpdates().add(new MapUpdate());
        loreService.validateLore(lore);
    }

    @Test
    public void assertThrowsMultipleProblems() {
        Lore lore = new Lore();
        lore.setName("a");
        assertThrows(ConstraintViolationException.class,() -> {loreService.validateLore(lore);});
    }

    @Test
    public void assertThrowsButNameChangeHelps() {

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

}
