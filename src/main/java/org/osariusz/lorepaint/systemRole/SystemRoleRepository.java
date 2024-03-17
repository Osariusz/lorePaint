package org.osariusz.lorepaint.systemRole;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemRoleRepository extends JpaRepository<SystemRole, Long> {
    public List<SystemRole> findAllByUser(@NotNull(message = "SystemRole user id cannot be null") User user);
}