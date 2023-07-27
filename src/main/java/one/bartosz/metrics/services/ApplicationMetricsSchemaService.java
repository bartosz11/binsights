package one.bartosz.metrics.services;

import jakarta.transaction.Transactional;
import one.bartosz.metrics.exceptions.DuplicateFieldException;
import one.bartosz.metrics.exceptions.EntityNotFoundException;
import one.bartosz.metrics.exceptions.SchemaVersionPresentException;
import one.bartosz.metrics.models.Application;
import one.bartosz.metrics.models.ApplicationMetricsSchema;
import one.bartosz.metrics.models.ApplicationMetricsSchemaCDO;
import one.bartosz.metrics.models.MetricFieldCDO;
import one.bartosz.metrics.repositories.ApplicationMetricsSchemaRepository;
import one.bartosz.metrics.repositories.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ApplicationMetricsSchemaService {

    private final ApplicationMetricsSchemaRepository applicationMetricsSchemaRepository;
    private final ApplicationRepository applicationRepository;

    public ApplicationMetricsSchemaService(ApplicationMetricsSchemaRepository applicationMetricsSchemaRepository, ApplicationRepository applicationRepository) {
        this.applicationMetricsSchemaRepository = applicationMetricsSchemaRepository;
        this.applicationRepository = applicationRepository;
    }


    public ApplicationMetricsSchema createApplicationSchema(UUID id, ApplicationMetricsSchemaCDO cdo) throws EntityNotFoundException, SchemaVersionPresentException, DuplicateFieldException {
        Application application = retrieveApplication(id);
        //make sure there's no other schema with the same version for this app
        String schemaVersion = cdo.getVersion();
        for (ApplicationMetricsSchema schema : application.getSchemas()) {
            if (schemaVersion.equalsIgnoreCase(schema.getVersion()))
                throw new SchemaVersionPresentException("Schema with given version is already present.");
        }
        //make sure there are no duplicate fields in schema
        List<String> fieldNames = new ArrayList<>();
        for (MetricFieldCDO metricField : cdo.getMetricFields()) {
            String name = metricField.getName();
            if (fieldNames.contains(name))
                throw new DuplicateFieldException("Field with name " + name + " is present more than once.");
            fieldNames.add(name);
        }
        //"assemble" the schema, I guess
        ApplicationMetricsSchema schema = new ApplicationMetricsSchema(cdo).setApplication(application);
        application.getSchemas().add(schema);
        //should cascade :troll:
        applicationRepository.save(application);
        return applicationMetricsSchemaRepository.save(schema);
    }

    public void deleteSchema(UUID id) throws EntityNotFoundException {
        ApplicationMetricsSchema applicationMetricsSchema = retrieveEntity(id);
        applicationMetricsSchemaRepository.delete(applicationMetricsSchema);
    }

    public ApplicationMetricsSchema getSchemaById(UUID id) throws EntityNotFoundException {
        return retrieveEntity(id);
    }
    //entity - whatever the service is supposed to handle
    private ApplicationMetricsSchema retrieveEntity(UUID id) throws EntityNotFoundException {
        return applicationMetricsSchemaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Application metrics schema with given ID couldn't be found."));
    }

    private Application retrieveApplication(UUID id) throws EntityNotFoundException {
        return applicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Application with given ID couldn't be found."));
    }
}
