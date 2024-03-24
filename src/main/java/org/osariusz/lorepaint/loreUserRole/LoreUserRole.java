package org.osariusz.lorepaint.loreUserRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.SystemRole.SystemRole;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.user.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "lore_user_roles")
public class LoreUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime granted_at;

    @NotNull(message = "LoreUserRole user id cannot be null")
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @NotNull(message = "LoreUserRole lore id cannot be null")
    @ManyToOne
    @JoinColumn(name="lore_id", nullable = false)
    private Lore lore;

    @NotNull(message = "SystemUserRole role string must not be null")
    @ManyToOne
    @JoinColumn(name="role_id", nullable = false)
    private LoreRole role;
}
