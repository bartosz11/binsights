package one.bartosz.metrics.services;

import jakarta.transaction.Transactional;
import one.bartosz.metrics.exceptions.EntityNotFoundException;
import one.bartosz.metrics.exceptions.InvalidNameException;
import one.bartosz.metrics.models.Application;
import one.bartosz.metrics.models.ApplicationCDO;
import one.bartosz.metrics.models.RenameRequest;
import one.bartosz.metrics.repositories.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ApplicationService {
    
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
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

    //A generic get-or-throw method
    public Application retrieveEntity(UUID id) throws EntityNotFoundException {
        return applicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Application with given ID couldn't be found."));
    }

}