package org.osariusz.lorepaint.mapUpdate;
import jakarta.persistence.*;
import lombok.*;
import org.osariusz.lorepaint.lore.Lore;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "map_updates")
public class MapUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String picture_path;

    @Column
    private String date;

    @Column
    private Long x;

    @Column
    private Long y;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime last_edit;

    @ManyToOne
    @JoinColumn(name="lore_id", nullable = false)
    private Lore lore;
}
