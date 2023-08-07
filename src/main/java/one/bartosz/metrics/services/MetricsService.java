package one.bartosz.metrics.services;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import jakarta.transaction.Transactional;
import one.bartosz.metrics.exceptions.EntityNotFoundException;
import one.bartosz.metrics.exceptions.SchemaDisabledException;
import one.bartosz.metrics.exceptions.SchemaValidationException;
import one.bartosz.metrics.models.Application;
import one.bartosz.metrics.models.ApplicationMetricsSchema;
import one.bartosz.metrics.models.MetricField;
import one.bartosz.metrics.models.MetricsPostRequest;
import one.bartosz.metrics.models.enums.MetricFieldType;
import one.bartosz.metrics.repositories.ApplicationMetricsSchemaRepository;
import one.bartosz.metrics.repositories.ApplicationRepository;
import one.bartosz.metrics.repositories.InfluxDBRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class MetricsService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMetricsSchemaRepository applicationMetricsSchemaRepository;
    private final InfluxDBRepository influxDBRepository;

    public MetricsService(ApplicationRepository applicationRepository, ApplicationMetricsSchemaRepository applicationMetricsSchemaRepository, InfluxDBRepository influxDBRepository) {
        this.applicationRepository = applicationRepository;
        this.applicationMetricsSchemaRepository = applicationMetricsSchemaRepository;
        this.influxDBRepository = influxDBRepository;
    }

    public void postMetrics(MetricsPostRequest metricsPostRequest) throws EntityNotFoundException, SchemaValidationException, SchemaDisabledException {
        UUID applicationId = UUID.fromString(metricsPostRequest.getApplication());
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new EntityNotFoundException("Application with given ID not found."));
        String schemaVersion = metricsPostRequest.getSchemaVersion();
        ApplicationMetricsSchema schema = applicationMetricsSchemaRepository.findByApplicationIdAndVersion(applicationId, schemaVersion)
                .orElseThrow(() -> new EntityNotFoundException("Schema with given version for given application couldn't be found."));
        if (!schema.isEnabled()) throw new SchemaDisabledException("Schema with given version is disabled.");
        HashMap<String, Object> data = metricsPostRequest.getData();
        validateDataAgainstSchema(data, simplifyFieldSet(schema.getFields()));
        //saving logic
        //add our reserved schema version field
        data.put("x_schema_version", schemaVersion);
        //create a point and write it
        Point point = Point.measurement(metricsPostRequest.getId()).addFields(data).time(Instant.now(), WritePrecision.NS);
        influxDBRepository.writePoint(point, application.getInfluxDBBucketID());
    }

    private void validateDataAgainstSchema(Map<String, Object> data, Map<String, MetricFieldType> schemaFields) throws SchemaValidationException {
        //technically there's no limitation but ehh I don't want clients to send it anyway
        //if a client sent valuable data in it'd get lost
        if (data.containsKey("x_schema_version")) throw new SchemaValidationException("Request contains reserved field x_schema_version.");
        if (data.size() > schemaFields.size()) throw new SchemaValidationException("Request contains excess fields that don't exist in the schema with the given version.");
        for (Map.Entry<String, MetricFieldType> field : schemaFields.entrySet()) {
            String fieldName = field.getKey();
            MetricFieldType fieldType = field.getValue();
            if (!data.containsKey(fieldName)) throw new SchemaValidationException("Field " + fieldName + " is missing in the request.");
            Object fieldValue = data.get(fieldName);
            String fieldValueClassName = fieldValue.getClass().getName();
            if (!fieldValueClassName.equals(fieldType.getJavaClassName())) {
                switch (fieldType) {
                    case STRING, BOOLEAN ->
                            throw new SchemaValidationException("Field " + fieldName + " in the request is using an incorrect type.");
                    case LONG -> {
                        //InfluxDB's library can cast int to long automatically, thank god, the only good thing it can do lmao
                        if (!fieldValueClassName.equals("java.lang.Integer")) {
                            throw new SchemaValidationException("Field " + fieldName + " in the request is using an incorrect type.");
                        }
                    }
                    case DOUBLE -> {
                        // The case above applies to float/double too
                        if (!fieldValueClassName.equals("java.lang.Float")) {
                            throw new SchemaValidationException("Field " + fieldName + " in the request is using an incorrect type.");
                        }
                    }
                }
            }
        }
    }

    private HashMap<String, MetricFieldType> simplifyFieldSet(Set<MetricField> set) {
        HashMap<String, MetricFieldType> map = new HashMap<>();
        set.forEach(field -> map.put(field.getName(), field.getType()));
        return map;
    }

}
