package org.osariusz.lorepaint.SystemRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

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

    @NotNull(message = "SystemRole string must not be null")
    @Column
    private String role;


    @NotNull(message = "SystemRole user id cannot be null")
    @OneToMany(mappedBy = "role")
    private List<SystemUserRole> userRoles;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(role);
    }

}
