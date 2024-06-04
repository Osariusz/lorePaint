package org.osariusz.lorepaint.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.user.User;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Entity
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Place name must not be null")
    @NotBlank(message = "Place name must not be blank")
    @Column
    private String name;

    @NotNull(message = "X must not be null")
    @Column
    private double x;

    @NotNull(message = "Y must not be null")
    @Column
    private double y;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime last_edit;

    @Column(name = "removed_at")
    private LocalDateTime removedAt;

    @Column
    @NotNull(message = "Place secret status must not be null")
    private Boolean isSecret;

    @JsonIgnore
    @NotNull(message = "Lore id must not be null")
    @ManyToOne
    @JoinColumn(name="lore_id", nullable = false)
    private Lore lore;

    @JsonIgnore
    @NotNull(message = "Owner id must not be null")
    @ManyToOne
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;


    @OneToMany(mappedBy = "place",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //TODO: fix @NotEmpty(message = "Place must have at least one place update")
    //@JsonManagedReference
    private List<PlaceUpdate> placeUpdates;

    public void setPoint(Point point) {
        setX(point.getX());
        setY(point.getY());
    }
}
