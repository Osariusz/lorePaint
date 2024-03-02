package org.osariusz.lorepaint.placeUpdate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.user.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "place_updates")
public class PlaceUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String picture_path;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime last_edit;

    @Column
    private LocalDateTime lore_date;

    @ManyToOne
    @JoinColumn(name="place_id", nullable = false)
    private Place place;

}
