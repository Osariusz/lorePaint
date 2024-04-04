package org.osariusz.lorepaint.lore;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoreRepository extends JpaRepository<Lore, Long> {
    public Lore findById(@NotNull(message = "Lore Id cannot be null") long id);
}
