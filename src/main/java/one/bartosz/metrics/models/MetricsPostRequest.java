package one.bartosz.metrics.models;

import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;

public class MetricsPostRequest {

    @NotBlank
    private String application;
    @NotBlank
    private String schemaVersion;
    //A form of identification - this app is made mostly for identifiable metrics but can also suit non-identifiable
    @NotBlank
    private String id;
    //No validation - schema can even be empty if user wants it to be
    private HashMap<String, Object> data;

    public String getApplication() {
        return application;
    }

    public MetricsPostRequest setApplication(String application) {
        this.application = application;
        return this;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public MetricsPostRequest setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
        return this;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public MetricsPostRequest setData(HashMap<String, Object> data) {
        this.data = data;
        return this;
    }

    public String getId() {
        return id;
    }

    public MetricsPostRequest setId(String id) {
        this.id = id;
        return this;
    }
}
