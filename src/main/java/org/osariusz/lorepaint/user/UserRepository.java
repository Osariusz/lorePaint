package org.osariusz.lorepaint.user;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User getByUsername(@NotNull(message = "Username must not be null") String username);

    public boolean existsByUsername(@NotNull(message = "Username must not be null") String username);
}
