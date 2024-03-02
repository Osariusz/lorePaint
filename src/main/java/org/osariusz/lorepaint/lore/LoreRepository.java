package org.osariusz.lorepaint.lore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoreRepository extends JpaRepository<Lore, Long> {
}
