package org.osariusz.lorepaint.loreUserRole;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoreUserRoleRepository extends JpaRepository<LoreUserRole, Long> {
    public List<LoreUserRole> findAllByLoreAndUser(@NotNull(message = "LoreUserRole lore id cannot be null") Lore lore, @NotNull(message = "LoreUserRole user id cannot be null") User user);
}