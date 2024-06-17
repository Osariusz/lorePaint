package org.osariusz.lorepaint.lore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.map.Map;
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

    @Column(name = "removed_at")
    private LocalDateTime removedAt;

    @JsonIgnore
    @NotNull(message = "Map must not be null")
    @OneToOne
    @JoinColumn(name = "map_id", nullable = false)
    private Map map;

    @JsonIgnore
    @OneToMany(mappedBy = "lore")
    private List<LoreUserRole> loreUserRoles;

    @JsonIgnore
    @OneToMany(mappedBy = "lore")
    @NotNull(message = "Place list must not be null")
    private List<Place> places;

}
