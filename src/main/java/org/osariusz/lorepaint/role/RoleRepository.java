package org.osariusz.lorepaint.role;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public List<Role> findAllByLoreAndUser(@NotNull(message = "Role lore id cannot be null") Lore lore, @NotNull(message = "Role user id cannot be null") User user);
}