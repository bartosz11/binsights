package one.bartosz.metrics.controllers;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Response> postMetrics(@RequestBody @Valid MetricsPostRequest metricsPostRequest, HttpServletRequest httpServletRequest) throws SchemaValidationException, EntityNotFoundException, SchemaDisabledException {
        String ip = getIPAddress(httpServletRequest);
        metricsService.postMetrics(metricsPostRequest, ip);
        return new Response(HttpStatus.NO_CONTENT).toResponseEntity();
    }
    //copied from LoggerInterceptor :)
    private String getIPAddress(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isEmpty()) return forwardedFor;
        return request.getRemoteAddr();
    }
}
