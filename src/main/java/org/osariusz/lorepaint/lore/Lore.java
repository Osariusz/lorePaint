package org.osariusz.lorepaint.lore;

import jakarta.persistence.*;
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

    @Column
    private String name;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime last_edit;

    @OneToMany(mappedBy = "lore")
    private List<MapUpdate> mapUpdates;

    @OneToMany(mappedBy = "lore")
    private List<Role> roles;

    @OneToMany(mappedBy = "lore")
    private List<Place> places;

}
