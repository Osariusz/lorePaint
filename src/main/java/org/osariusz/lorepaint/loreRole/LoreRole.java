package org.osariusz.lorepaint.loreRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.user.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "lore_roles")
public class LoreRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum UserRole {
        GM,
        MEMBER
    }

    @NotNull(message = "LoreRole string must not be null")
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    private LocalDateTime granted_at;

    @NotNull(message = "LoreRole user id cannot be null")
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @NotNull(message = "LoreRole lore id cannot be null")
    @ManyToOne
    @JoinColumn(name="lore_id", nullable = false)
    private Lore lore;
}
