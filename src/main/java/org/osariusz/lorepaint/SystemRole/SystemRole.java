package org.osariusz.lorepaint.SystemRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.osariusz.lorepaint.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Entity
@Table(name = "system_roles")
public class SystemRole {

    public SystemRole() {
        this.setUserRoles(new ArrayList<>());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum UserRole {
        USER,
        ADMIN
    }

    @NotNull(message = "SystemRole string must not be null")
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;


    @NotNull(message = "SystemRole user id cannot be null")
    @OneToMany(mappedBy = "role")
    private List<SystemUserRole> userRoles;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(role.name());
    }

}
