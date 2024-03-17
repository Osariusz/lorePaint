package org.osariusz.lorepaint.systemUserRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "system_user_roles")
public class SystemUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum UserRole {
        USER,
        ADMIN
    }

    @NotNull(message = "LoreUserRole string must not be null")
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    private LocalDateTime granted_at;

    @NotNull(message = "LoreUserRole user id cannot be null")
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(role.name());
    }

}
