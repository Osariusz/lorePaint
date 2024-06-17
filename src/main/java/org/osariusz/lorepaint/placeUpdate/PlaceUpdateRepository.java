package org.osariusz.lorepaint.placeUpdate;

import org.osariusz.lorepaint.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceUpdateRepository extends JpaRepository<PlaceUpdate, Long> {
    List<PlaceUpdate> findAllByPlace(Place place);
}