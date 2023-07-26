package one.bartosz.metrics.repositories;

import one.bartosz.metrics.models.MetricField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MetricFieldRepository extends JpaRepository<MetricField, UUID> {
}
