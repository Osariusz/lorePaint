package org.osariusz.lorepaint.place;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.lore.Lore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findAllByRemovedAtIsNull();

    List<Place> findAllByLoreAndRemovedAtIsNull(@NotNull(message = "Place lore id cannot be null") Lore lore);

    Optional<Place> findByIdAndRemovedAtIsNull(@NotNull(message = "Place id cannot be null") Long id);
}