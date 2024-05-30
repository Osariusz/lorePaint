package org.osariusz.lorepaint.place;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaceDateGetDTO {
    private LocalDateTime date;
}
