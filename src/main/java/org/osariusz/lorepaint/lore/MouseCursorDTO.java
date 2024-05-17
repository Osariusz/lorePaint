package org.osariusz.lorepaint.lore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MouseCursorDTO {
    private String username;
    private double[] coordinates;
}
