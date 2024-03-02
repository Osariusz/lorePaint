package org.osariusz.lorepaint.lore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.role.Role;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "lores")
public class Lore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Lore name must not be null")
    @NotBlank(message = "Lore name must not be blank")
    @Column
    private String name;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime last_edit;

    @NotEmpty(message = "Lore must have at least 1 map update")
    @OneToMany(mappedBy = "lore")
    private List<MapUpdate> mapUpdates;

    @OneToMany(mappedBy = "lore")
    private List<Role> roles;

    @OneToMany(mappedBy = "lore")
    private List<Place> places;

}
