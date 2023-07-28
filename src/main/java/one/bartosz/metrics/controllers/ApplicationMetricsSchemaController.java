package one.bartosz.metrics.controllers;

import one.bartosz.metrics.exceptions.EntityNotFoundException;
import one.bartosz.metrics.models.ApplicationMetricsSchema;
import one.bartosz.metrics.models.Response;
import one.bartosz.metrics.services.ApplicationMetricsSchemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/schema")
public class ApplicationMetricsSchemaController {

    private final ApplicationMetricsSchemaService applicationMetricsSchemaService;

    public ApplicationMetricsSchemaController(ApplicationMetricsSchemaService applicationMetricsSchemaService) {
        this.applicationMetricsSchemaService = applicationMetricsSchemaService;
    }

    //Create and list all by app id endpoints are located in ApplicationController
    //There's no update endpoint - for some reason we allow multiple schemas and versioning of them

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Response> deleteSchemaById(@PathVariable UUID id) throws EntityNotFoundException {
        applicationMetricsSchemaService.deleteSchema(id);
        return new Response(HttpStatus.NO_CONTENT).toResponseEntity();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Response> getSchemaById(@PathVariable UUID id) throws EntityNotFoundException {
        ApplicationMetricsSchema schemaById = applicationMetricsSchemaService.getSchemaById(id);
        return new Response(HttpStatus.OK).addAdditionalData(schemaById).toResponseEntity();
    }

    @PatchMapping("/{id}/status")
    @ResponseBody
    public ResponseEntity<Response> changeSchemaStatus(@PathVariable UUID id, @RequestParam boolean enable) throws EntityNotFoundException {
        applicationMetricsSchemaService.changeSchemaStatus(id, enable);
        return new Response(HttpStatus.NO_CONTENT).toResponseEntity();
    }
}
