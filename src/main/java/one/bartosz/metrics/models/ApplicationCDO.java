package one.bartosz.metrics.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

//Creation Data Object :)
//very "me" thing to create
public class ApplicationCDO {
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    private String influxDBBucketName;
    @NotNull
    @NotEmpty
    @NotBlank
    private String influxDBRetention;

    public String getName() {
        return name;
    }

    public ApplicationCDO setName(String name) {
        this.name = name;
        return this;
    }

    public String getInfluxDBBucketName() {
        return influxDBBucketName;
    }

    public ApplicationCDO setInfluxDBBucketName(String influxDBBucketName) {
        this.influxDBBucketName = influxDBBucketName;
        return this;
    }

    public String getInfluxDBRetention() {
        return influxDBRetention;
    }

    public ApplicationCDO setInfluxDBRetention(String influxDBRetention) {
        this.influxDBRetention = influxDBRetention;
        return this;
    }
}