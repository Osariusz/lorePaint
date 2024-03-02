package org.osariusz.lorepaint.mapUpdate;

import org.osariusz.lorepaint.lore.Lore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapUpdateRepository extends JpaRepository<MapUpdate, Long> {
}
