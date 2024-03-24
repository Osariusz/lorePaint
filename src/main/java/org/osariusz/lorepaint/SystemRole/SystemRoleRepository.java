package org.osariusz.lorepaint.SystemRole;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.osariusz.lorepaint.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemRoleRepository extends JpaRepository<SystemRole, Long> {
    public SystemRole findByRole(@NotNull(message = "SystemUserRole role cannot be null") SystemRole.UserRole role);

    public boolean existsByRole(@NotNull(message = "SystemUserRole role cannot be null") SystemRole.UserRole role);
}