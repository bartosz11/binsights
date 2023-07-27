package one.bartosz.metrics.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import one.bartosz.metrics.models.enums.MetricFieldType;

public class MetricFieldCDO {
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    private String description;
    @NotNull
    private MetricFieldType type;

    public String getName() {
        return name;
    }

    public MetricFieldCDO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MetricFieldCDO setDescription(String description) {
        this.description = description;
        return this;
    }

    public MetricFieldType getType() {
        return type;
    }

    public MetricFieldCDO setType(MetricFieldType type) {
        this.type = type;
        return this;
    }
}
