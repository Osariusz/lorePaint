package org.osariusz.lorepaint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.shared.DateGetDTO;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;

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
