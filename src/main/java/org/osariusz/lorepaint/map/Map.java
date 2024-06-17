package org.osariusz.lorepaint.map;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.mapUpdate.MapUpdate;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "maps")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime created_at;

    @OneToOne(mappedBy = "map")
    private Lore lore;

    @NotEmpty(message = "Place must have at least one place update")
    @OneToMany(mappedBy = "map", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<MapUpdate> mapUpdates;
}
