package one.bartosz.metrics.controllers;

import jakarta.validation.Valid;
import one.bartosz.metrics.exceptions.DuplicateFieldException;
import one.bartosz.metrics.exceptions.EntityNotFoundException;
import one.bartosz.metrics.exceptions.InvalidNameException;
import one.bartosz.metrics.exceptions.SchemaVersionPresentException;
import one.bartosz.metrics.models.*;
import one.bartosz.metrics.services.ApplicationMetricsSchemaService;
import one.bartosz.metrics.services.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationMetricsSchemaService applicationMetricsSchemaService;

    public ApplicationController(ApplicationService applicationService, ApplicationMetricsSchemaService applicationMetricsSchemaService) {
        this.applicationService = applicationService;
        this.applicationMetricsSchemaService = applicationMetricsSchemaService;
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Response> createApplication(@RequestBody ApplicationCDO cdo) throws InvalidNameException {
        Application application = applicationService.createApplication(cdo);
        return new Response(HttpStatus.CREATED).addAdditionalData(application).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Response> deleteApplication(@PathVariable UUID id) throws EntityNotFoundException {
        applicationService.deleteApplication(id);
        return new Response(HttpStatus.NO_CONTENT).toResponseEntity();
    }

    @GetMapping("/{identifier}")
    @ResponseBody
    public ResponseEntity<Response> getApplicationById(@PathVariable String identifier) throws EntityNotFoundException {
        try {
            UUID uuid = UUID.fromString(identifier);
            Application applicationById = applicationService.getApplicationById(uuid);
            return new Response(HttpStatus.OK).addAdditionalData(applicationById).toResponseEntity();
        } catch (IllegalArgumentException e) {
            Application applicationByName = applicationService.getApplicationByName(identifier);
            return new Response(HttpStatus.OK).addAdditionalData(applicationByName).toResponseEntity();
        }
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Response> getAllApplications() {
        List<Application> allApplications = applicationService.getAllApplications();
        return new Response(HttpStatus.OK).addAdditionalData(allApplications).toResponseEntity();
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Response> renameApplication(@PathVariable UUID id, @Valid @RequestBody RenameRequest renameRequest) throws InvalidNameException, EntityNotFoundException {
        Application application = applicationService.renameApplication(id, renameRequest);
        return new Response(HttpStatus.OK).addAdditionalData(application).toResponseEntity();
    }

    @PostMapping("/{id}/schema")
    @ResponseBody
    public ResponseEntity<Response> createNewSchema(@PathVariable UUID id, @RequestBody @Valid ApplicationMetricsSchemaCDO cdo) throws SchemaVersionPresentException, DuplicateFieldException, EntityNotFoundException {
        ApplicationMetricsSchema applicationMetricsSchema = applicationMetricsSchemaService.createApplicationSchema(id, cdo);
        return new Response(HttpStatus.CREATED).addAdditionalData(applicationMetricsSchema).toResponseEntity();
    }
    @GetMapping("/{id}/schemas")
    @ResponseBody
    public ResponseEntity<Response> getAllAppSchemas(@PathVariable UUID id) throws EntityNotFoundException {
        List<ApplicationMetricsSchema> allApplicationSchemasByApplication = applicationMetricsSchemaService.getAllApplicationSchemasByApplication(id);
        return new Response(HttpStatus.OK).addAdditionalData(allApplicationSchemasByApplication).toResponseEntity();
    }
}
