package one.bartosz.metrics.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String influxDBBucketName;
    //todo validation of this value
    private String influxDBRetention;
    @OneToMany(cascade = CascadeType.ALL)
    //there's a dedicated endpoint for fetching schemas of an application
    @JsonIgnore
    private Set<ApplicationMetricsSchema> schemas;

    public Application() {}

    public Application(ApplicationCDO cdo) {
        this.name = cdo.getName();
        String dbBucketName = cdo.getInfluxDBBucketName();
        this.influxDBBucketName = !(dbBucketName == null || dbBucketName.isBlank() || dbBucketName.isEmpty()) ? dbBucketName : this.name;
        this.influxDBRetention = cdo.getInfluxDBRetention();
    }

    public UUID getId() {
        return id;
    }

    public Application setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Application setName(String name) {
        this.name = name;
        return this;
    }

    public Set<ApplicationMetricsSchema> getSchemas() {
        return schemas;
    }

    public Application setSchemas(Set<ApplicationMetricsSchema> schemas) {
        this.schemas = schemas;
        return this;
    }

    public String getInfluxDBBucketName() {
        return influxDBBucketName;
    }

    public Application setInfluxDBBucketName(String influxDBBucketName) {
        this.influxDBBucketName = influxDBBucketName;
        return this;
    }

    public String getInfluxDBRetention() {
        return influxDBRetention;
    }

    public Application setInfluxDBRetention(String influxDBRetention) {
        this.influxDBRetention = influxDBRetention;
        return this;
    }
}
