package one.bartosz.metrics.services;

import jakarta.transaction.Transactional;
import one.bartosz.metrics.exceptions.DuplicateFieldException;
import one.bartosz.metrics.exceptions.EntityNotFoundException;
import one.bartosz.metrics.exceptions.InvalidNameException;
import one.bartosz.metrics.exceptions.SchemaVersionPresentException;
import one.bartosz.metrics.models.*;
import one.bartosz.metrics.repositories.ApplicationMetricsSchemaRepository;
import one.bartosz.metrics.repositories.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ApplicationService {
    
    private final ApplicationRepository applicationRepository;
    private final ApplicationMetricsSchemaRepository applicationMetricsSchemaRepository;

    public ApplicationService(ApplicationRepository applicationRepository, ApplicationMetricsSchemaRepository applicationMetricsSchemaRepository) {
        this.applicationRepository = applicationRepository;
        this.applicationMetricsSchemaRepository = applicationMetricsSchemaRepository;
    }
    
    public Application createApplication(ApplicationCDO cdo) throws InvalidNameException {
        String name = cdo.getName();
        if (name == null || name.isEmpty() || name.isBlank()) throw new InvalidNameException("Name can't be empty or blank.");
        Application application = new Application(cdo);
        return applicationRepository.save(application);
    }

    public void deleteApplication(UUID id) throws EntityNotFoundException {
        Application application = retrieveEntity(id);
        applicationRepository.delete(application);
    }

    public Application getApplicationById(UUID id) throws EntityNotFoundException {
        return retrieveEntity(id);
    }

    public Application getApplicationByName(String name) throws EntityNotFoundException {
        return applicationRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Application with given ID couldn't be found."));
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application renameApplication(UUID id, RenameRequest renameRequest) throws InvalidNameException, EntityNotFoundException {
        String name = renameRequest.getName();
        if (name == null || name.isEmpty() || name.isBlank()) throw new InvalidNameException("New name can't be empty or blank.");
        Application application = retrieveEntity(id);
        return applicationRepository.save(application.setName(name));
    }

    public ApplicationMetricsSchema createApplicationSchema(UUID id, ApplicationMetricsSchemaCDO cdo) throws EntityNotFoundException, SchemaVersionPresentException, DuplicateFieldException {
        Application application = retrieveEntity(id);
        //make sure there's no other schema with the same version for this app
        String schemaVersion = cdo.getVersion();
        for (ApplicationMetricsSchema schema : application.getSchemas()) {
            if (schemaVersion.equalsIgnoreCase(schema.getVersion())) throw new SchemaVersionPresentException("Schema with given version is already present.");
        }
        //make sure there are no duplicate fields in schema
        List<String> fieldNames = new ArrayList<>();
        for (MetricFieldCDO metricField : cdo.getMetricFields()) {
            String name = metricField.getName();
            if (fieldNames.contains(name)) throw new DuplicateFieldException("Field with name "+name+" is present more than once.");
            fieldNames.add(name);
        }
        //"assemble" the schema, I guess
        ApplicationMetricsSchema schema = new ApplicationMetricsSchema(cdo).setApplication(application);
        application.getSchemas().add(schema);
        //should cascade :troll:
        applicationRepository.save(application);
        return applicationMetricsSchemaRepository.save(schema);
    }

    //A generic get-or-throw method
    private Application retrieveEntity(UUID id) throws EntityNotFoundException {
        return applicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Application with given ID couldn't be found."));
    }

}