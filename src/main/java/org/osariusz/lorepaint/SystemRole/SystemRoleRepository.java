package org.osariusz.lorepaint.SystemRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRoleRepository extends JpaRepository<SystemRole, Long> {
    public SystemRole findByRole(String role);

    public boolean existsByRole(String role);
}