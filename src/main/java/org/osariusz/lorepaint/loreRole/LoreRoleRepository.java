package org.osariusz.lorepaint.loreRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoreRoleRepository extends JpaRepository<LoreRole, Long> {
    public LoreRole findByRole(String role);

    public boolean existsByRole(String role);
}