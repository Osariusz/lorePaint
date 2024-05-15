package org.osariusz.lorepaint.place;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaceCreateDTO {
    private String name;
    private String description;
    private long loreId;
    private LocalDateTime creationLoreDate;
    private Point point;
}
