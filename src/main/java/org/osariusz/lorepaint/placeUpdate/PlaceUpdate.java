package org.osariusz.lorepaint.placeUpdate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.place.Place;

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

    @NotNull(message = "Place update new description must not be null")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String picture_path;

    @Column
    private LocalDateTime created_at;

    @NotNull(message = "Placeupdate lore date must not be null")
    @Column
    private LocalDateTime lore_date;

    @NotNull(message = "Place update place id must not be null")
    @ManyToOne
    @JoinColumn(name="place_id", nullable = false)
    private Place place;

}
