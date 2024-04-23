package org.osariusz.lorepaint.shared;

import org.osariusz.lorepaint.lore.Lore;

import java.sql.Timestamp;

public class PlaceCreateDTO {
    private String name;
    private String description;
    private Lore lore;
    private Timestamp creationLoreDate;
}
