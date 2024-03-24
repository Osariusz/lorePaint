package org.osariusz.lorepaint.loreRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.osariusz.lorepaint.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Entity
@Table(name = "lore_roles")
public class LoreRole {

    public LoreRole() {
        this.setUserRoles(new ArrayList<>());
    }

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

    @NotNull(message = "SystemRole user id cannot be null")
    @OneToMany(mappedBy = "role")
    private List<LoreUserRole> userRoles;

}
