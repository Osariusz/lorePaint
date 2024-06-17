package org.osariusz.lorepaint.systemUserRole;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.SystemRole.SystemRole;
import org.osariusz.lorepaint.user.User;
import org.springframework.security.core.GrantedAuthority;

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

    @Column
    private LocalDateTime granted_at;

    @JsonIgnore
    @NotNull(message = "SystemUserRole user id cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @NotNull(message = "SystemUserRole role string must not be null")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private SystemRole role;

    public GrantedAuthority toAuthority() {
        return role.toAuthority();
    }
}
