package org.osariusz.lorepaint.user;

import org.osariusz.lorepaint.mapUpdate.MapUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
