package one.bartosz.metrics.models;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String influxDBBucketName;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ApplicationMetricsSchema> schemas;

    public Application() {}

    public Application(ApplicationCDO cdo) {
        this.name = cdo.getName();
        String dbBucketName = cdo.getInfluxDBBucketName();
        this.influxDBBucketName = !(dbBucketName == null || dbBucketName.isBlank() || dbBucketName.isEmpty()) ? dbBucketName : this.name;
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
}
