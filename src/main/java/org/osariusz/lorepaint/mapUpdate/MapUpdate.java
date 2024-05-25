package org.osariusz.lorepaint.mapUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.antlr.v4.runtime.misc.Interval;
import org.hibernate.dialect.PostgreSQLIntervalSecondJdbcType;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.map.Map;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "map_updates")
public class MapUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String picture_path;

    @NotNull(message = "Lore date must not be null")
    @Column
    private LocalDateTime lore_date;

    @NotNull(message = "X coordinate must not be null")
    @Column
    private double x;

    @NotNull(message = "Y coordinate must not be null")
    @Column
    private double y;

    @Column
    private LocalDateTime created_at;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="map_id", nullable = false)
    private Map map;
}
