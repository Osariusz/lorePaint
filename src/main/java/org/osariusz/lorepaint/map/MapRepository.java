package org.osariusz.lorepaint.map;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.lore.Lore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {
    List<Map> findAllByLore(@NotNull(message = "Place lore id cannot be null") Lore lore);

}
