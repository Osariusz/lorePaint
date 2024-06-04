package org.osariusz.lorepaint.user;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsernameAndRemovedAtIsNull(@NotNull(message = "Username must not be null") String username);

    public Optional<User> findByUsername(@NotNull(message = "Username must not be null") String username);

    public boolean existsByUsernameAndRemovedAtIsNull(@NotNull(message = "Username must not be null") String username);

    public boolean existsByUsername(@NotNull(message = "Username must not be null") String username);

    public List<User> findAllByRemovedAtIsNull();
}
