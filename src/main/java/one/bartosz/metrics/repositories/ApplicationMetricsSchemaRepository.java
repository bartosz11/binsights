package one.bartosz.metrics.repositories;

import one.bartosz.metrics.models.Application;
import one.bartosz.metrics.models.ApplicationMetricsSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationMetricsSchemaRepository extends JpaRepository<ApplicationMetricsSchema, UUID> {
    @Query("SELECT ms FROM ApplicationMetricsSchema ms LEFT JOIN FETCH ms.fields WHERE ms.application = :application")
    List<ApplicationMetricsSchema> findAllApplicationMetricsSchemasByApplication(Application application);
    @Override
    @Query("SELECT ms FROM ApplicationMetricsSchema ms LEFT JOIN FETCH ms.fields WHERE ms.id = :id")
    Optional<ApplicationMetricsSchema> findById(UUID id);
    @Query("SELECT ms FROM ApplicationMetricsSchema ms LEFT JOIN FETCH ms.fields WHERE ms.application.id = :applicationId AND version = :version")
    Optional<ApplicationMetricsSchema> findByApplicationIdAndVersion(UUID applicationId, String version);
}
