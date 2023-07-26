package one.bartosz.metrics.models;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
public class ApplicationMetricsSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String version;
    private boolean enabled;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<MetricField> fields;
    @ManyToOne
    private Application application;

    public UUID getId() {
        return id;
    }

    public ApplicationMetricsSchema setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ApplicationMetricsSchema setVersion(String version) {
        this.version = version;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ApplicationMetricsSchema setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Set<MetricField> getFields() {
        return fields;
    }

    public ApplicationMetricsSchema setFields(Set<MetricField> fields) {
        this.fields = fields;
        return this;
    }

    public Application getService() {
        return application;
    }

    public ApplicationMetricsSchema setService(Application service) {
        this.application = service;
        return this;
    }
}
