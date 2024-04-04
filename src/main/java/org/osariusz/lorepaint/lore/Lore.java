package org.osariusz.lorepaint.lore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.map.Map;
import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.osariusz.lorepaint.place.Place;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "lores")
public class Lore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Lore name must not be null")
    @NotBlank(message = "Lore name must not be blank")
    @Column
    private String name;

    @NotNull(message = "Lore description must not be null")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime last_edit;

    @Column
    private LocalDateTime removed_at;

    @JsonIgnore
    @NotNull(message = "Map update list must not be null")
    @OneToOne
    @JoinColumn(name="map_id", nullable = false)
    private Map map;

    @JsonIgnore
    @OneToMany(mappedBy = "lore")
    @NotNull(message = "LoreUserRole list must not be null")
    private List<LoreUserRole> loreUserRoles;

    @JsonIgnore
    @OneToMany(mappedBy = "lore")
    @NotNull(message = "Place list must not be null")
    private List<Place> places;

}
