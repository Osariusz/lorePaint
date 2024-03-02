package org.osariusz.lorepaint.place;

import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}