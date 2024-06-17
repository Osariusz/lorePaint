package org.osariusz.lorepaint.loreRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;

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

    @NotNull(message = "LoreRole string must not be null")
    @Column
    private String role;

    @NotNull(message = "SystemRole user id cannot be null")
    @OneToMany(mappedBy = "role")
    private List<LoreUserRole> userRoles;

}
