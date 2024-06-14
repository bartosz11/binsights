package one.bartosz.metrics.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Creation Data Object :)
//very "me" thing to create
public class ApplicationCDO {
    @NotBlank(message = "Name cannot be blank.")
    private String name;
    private String influxDBBucketName;
    @NotNull(message = "InfluxDB retention value cannot be null.")
    @Min(value = 0, message = "InfluxDB retention value must be greater than 0.")
    private int influxDBRetention;

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

    public int getInfluxDBRetention() {
        return influxDBRetention;
    }

    public ApplicationCDO setInfluxDBRetention(int influxDBRetention) {
        this.influxDBRetention = influxDBRetention;
        return this;
    }
}