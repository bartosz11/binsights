package one.bartosz.metrics.repositories;

import one.bartosz.metrics.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserRepository, UUID> {

    Optional<User> findByUsername(String username);
}
