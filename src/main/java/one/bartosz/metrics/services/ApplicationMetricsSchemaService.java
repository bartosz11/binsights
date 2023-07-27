package one.bartosz.metrics.services;

import jakarta.transaction.Transactional;
import one.bartosz.metrics.exceptions.EntityNotFoundException;
import one.bartosz.metrics.models.Application;
import one.bartosz.metrics.models.ApplicationMetricsSchema;
import one.bartosz.metrics.repositories.ApplicationMetricsSchemaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ApplicationMetricsSchemaService {

    private final ApplicationMetricsSchemaRepository applicationMetricsSchemaRepository;

    public ApplicationMetricsSchemaService(ApplicationMetricsSchemaRepository applicationMetricsSchemaRepository) {
        this.applicationMetricsSchemaRepository = applicationMetricsSchemaRepository;
    }

    //there's no creation method - it happens in ApplicationController and ApplicationService - don't ask why, I'm too dumb to explain my thought process

    public void deleteSchema(UUID id) throws EntityNotFoundException {
        ApplicationMetricsSchema applicationMetricsSchema = retrieveEntity(id);
        applicationMetricsSchemaRepository.delete(applicationMetricsSchema);
    }

    public ApplicationMetricsSchema getSchemaById(UUID id) throws EntityNotFoundException {
        return retrieveEntity(id);
    }

    private ApplicationMetricsSchema retrieveEntity(UUID id) throws EntityNotFoundException {
        return applicationMetricsSchemaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Application metrics schema with given ID couldn't be found."));
    }
}
