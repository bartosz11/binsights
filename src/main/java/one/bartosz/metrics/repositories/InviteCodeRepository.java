package one.bartosz.metrics.repositories;

import one.bartosz.metrics.models.InviteCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteCodeRepository extends JpaRepository<InviteCode, String> {
}
