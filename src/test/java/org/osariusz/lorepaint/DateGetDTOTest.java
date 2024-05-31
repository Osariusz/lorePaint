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
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.shared.DateGetDTO;
import org.osariusz.lorepaint.shared.UserMapService;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserDTO;
import org.osariusz.lorepaint.user.UserRepository;
import org.osariusz.lorepaint.utils.RoleNames;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class DateGetDTOTest {

    @Spy
    @InjectMocks
    private ModelMapper modelMapper;

    @Test
    public void DateGetLoreDateMap() {
        DateGetDTO dateGetDTO = new DateGetDTO();
        dateGetDTO.setLore_date(LocalDateTime.now());
        PlaceUpdate placeUpdate = modelMapper.map(dateGetDTO, PlaceUpdate.class);
        assert placeUpdate.getLore_date() == dateGetDTO.getLore_date();
    }
}
