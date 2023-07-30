package one.bartosz.metrics.repositories;

import one.bartosz.metrics.models.InviteCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InviteCodeRepository extends JpaRepository<InviteCode, String> {

    List<InviteCode> findAllByUserId(UUID id);
}
