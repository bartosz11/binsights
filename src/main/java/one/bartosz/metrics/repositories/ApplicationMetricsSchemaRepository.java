package one.bartosz.metrics.repositories;

import one.bartosz.metrics.models.ApplicationMetricsSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationMetricsSchemaRepository extends JpaRepository<ApplicationMetricsSchema, UUID> {

    List<ApplicationMetricsSchema> findAllApplicationMetricsSchemasByApplicationId(UUID id);
}
