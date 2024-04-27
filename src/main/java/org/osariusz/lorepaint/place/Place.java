package org.osariusz.lorepaint.place;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.user.User;

import java.awt.*;
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

    @Column
    private LocalDateTime removed_at;

    @Column
    private Boolean isSecret;

    @NotNull(message = "Lore id must not be null")
    @ManyToOne
    @JoinColumn(name="lore_id", nullable = false)
    private Lore lore;

    @NotNull(message = "Owner id must not be null")
    @ManyToOne
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;


    @OneToMany(mappedBy = "place",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@NotEmpty(message = "Place must have at least one place update")
    //@JsonManagedReference
    private List<PlaceUpdate> placeUpdates;
}
