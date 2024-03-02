package org.osariusz.lorepaint.place;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.user.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long x;

    @Column
    private Long y;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime last_edit;

    @Column
    private boolean isSecret;

    @ManyToOne
    @JoinColumn(name="lore_id", nullable = false)
    private Lore lore;

    @ManyToOne
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "place")
    private List<PlaceUpdate> placeUpdates;
}
