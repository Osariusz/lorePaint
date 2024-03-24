package org.osariusz.lorepaint.loreRole;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoreRoleRepository extends JpaRepository<LoreRole, Long> {
    public LoreRole findByRole(@NotNull(message = "LoreRole role cannot be null") LoreRole.UserRole role);

    public boolean existsByRole(@NotNull(message = "LoreRole role cannot be null") LoreRole.UserRole role);
}