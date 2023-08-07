package one.bartosz.metrics.controllers;

import jakarta.validation.Valid;
import one.bartosz.metrics.exceptions.EntityNotFoundException;
import one.bartosz.metrics.exceptions.SchemaDisabledException;
import one.bartosz.metrics.exceptions.SchemaValidationException;
import one.bartosz.metrics.models.MetricsPostRequest;
import one.bartosz.metrics.models.Response;
import one.bartosz.metrics.services.MetricsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<Response> postMetrics(@RequestBody @Valid MetricsPostRequest metricsPostRequest) throws SchemaValidationException, EntityNotFoundException, SchemaDisabledException {
        metricsService.postMetrics(metricsPostRequest);
        return new Response(HttpStatus.NO_CONTENT).toResponseEntity();
    }
}
