package org.osariusz.lorepaint.role;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.user.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Role string must not be null")
    @NotBlank(message = "Role string must not be blank")
    @Column
    private String role;

    @Column
    private LocalDateTime granted_at;

    @NotNull(message = "Role user id cannot be null")
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @NotNull(message = "Role lore id cannot be null")
    @ManyToOne
    @JoinColumn(name="lore_id", nullable = false)
    private Lore lore;
}
