package org.osariusz.lorepaint.lore;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoreRepository extends JpaRepository<Lore, Long> {
    public Optional<Lore> findByIdAndRemovedAtIsNull(@NotNull(message = "Lore Id cannot be null") long id);

    public List<Lore> findAllByRemovedAtIsNull();
}
