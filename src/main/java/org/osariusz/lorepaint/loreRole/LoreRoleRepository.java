package org.osariusz.lorepaint.loreRole;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoreRoleRepository extends JpaRepository<LoreRole, Long> {
    public List<LoreRole> findAllByLoreAndUser(@NotNull(message = "LoreRole lore id cannot be null") Lore lore, @NotNull(message = "LoreRole user id cannot be null") User user);
}