package org.osariusz.lorepaint.shared;

import lombok.Getter;
import lombok.Setter;
import org.osariusz.lorepaint.user.User;
import org.springframework.data.geo.Point;

import java.awt.*;
import java.sql.Timestamp;
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
