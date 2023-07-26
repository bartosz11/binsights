package one.bartosz.metrics.models;

import jakarta.persistence.*;
import one.bartosz.metrics.models.enums.MetricFieldType;

import java.util.UUID;

@Entity
public class MetricField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;
    private String description;
    private MetricFieldType type;
    @ManyToOne
    private ApplicationMetricsSchema schema;

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
