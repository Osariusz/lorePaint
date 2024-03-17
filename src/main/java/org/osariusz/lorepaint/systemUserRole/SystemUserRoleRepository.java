package org.osariusz.lorepaint.systemUserRole;

import jakarta.validation.constraints.NotNull;
import org.osariusz.lorepaint.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemUserRoleRepository extends JpaRepository<SystemUserRole, Long> {
    public List<SystemUserRole> findAllByUser(@NotNull(message = "SystemUserRole user id cannot be null") User user);
}