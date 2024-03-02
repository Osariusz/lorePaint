package org.osariusz.lorepaint.role;

import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}