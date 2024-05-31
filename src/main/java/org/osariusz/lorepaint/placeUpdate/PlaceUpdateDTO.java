package org.osariusz.lorepaint.placeUpdate;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaceUpdateDTO {
    private String description;
    private String picture_path;
    private LocalDateTime lore_date;
    private long placeId;
}
