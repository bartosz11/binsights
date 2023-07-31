package one.bartosz.metrics.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import one.bartosz.metrics.models.enums.MetricFieldType;

import java.util.UUID;

@Entity
@Table(name = "metric_fields")
public class MetricField {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private MetricFieldType type;
    @ManyToOne
    @JsonIgnore
    private ApplicationMetricsSchema schema;

    public MetricField() {}

    public MetricField(MetricFieldCDO cdo) {
        this.name = cdo.getName();
        this.description = cdo.getDescription();
        this.type = cdo.getType();
    }

    public UUID getId() {
        return id;
    }

    public MetricField setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MetricField setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MetricField setDescription(String description) {
        this.description = description;
        return this;
    }

    public MetricFieldType getType() {
        return type;
    }

    public MetricField setType(MetricFieldType type) {
        this.type = type;
        return this;
    }

    public ApplicationMetricsSchema getSchema() {
        return schema;
    }

    public MetricField setSchema(ApplicationMetricsSchema schema) {
        this.schema = schema;
        return this;
    }
}
