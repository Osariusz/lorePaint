package org.osariusz.lorepaint.systemRole;

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
@Table(name = "system_roles")
public class SystemRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum UserRole {
        USER,
        ADMIN
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

}
