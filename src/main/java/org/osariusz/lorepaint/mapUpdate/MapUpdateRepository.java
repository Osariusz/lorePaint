package org.osariusz.lorepaint.mapUpdate;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.map.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapUpdateRepository extends JpaRepository<MapUpdate, Long> {
    List<MapUpdate> findAllByMap(@NotNull(message = "Map id cannot be null") Map map);
}
