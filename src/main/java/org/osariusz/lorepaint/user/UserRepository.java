package org.osariusz.lorepaint.user;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndRemovedAtIsNull(@NotNull(message = "Username must not be null") String username);

    Optional<User> findByUsername(@NotNull(message = "Username must not be null") String username);

    boolean existsByUsernameAndRemovedAtIsNull(@NotNull(message = "Username must not be null") String username);

    boolean existsByUsername(@NotNull(message = "Username must not be null") String username);

    List<User> findAllByRemovedAtIsNull();
}
