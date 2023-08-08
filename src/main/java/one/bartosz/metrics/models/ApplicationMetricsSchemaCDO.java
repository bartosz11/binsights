package one.bartosz.metrics.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class ApplicationMetricsSchemaCDO {

    @NotBlank
    private String version;
    @NotNull
    private boolean enabled;
    //Using a Set to ensure there are no duplicates partially
    @NotNull
    private Set<@Valid MetricFieldCDO> metricFields;
    @NotNull
    private boolean collectIPAddresses;

    public String getVersion() {
        return version;
    }

    public ApplicationMetricsSchemaCDO setVersion(String version) {
        this.version = version;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ApplicationMetricsSchemaCDO setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Set<@Valid MetricFieldCDO> getMetricFields() {
        return metricFields;
    }

    public ApplicationMetricsSchemaCDO setMetricFields(Set<MetricFieldCDO> metricFields) {
        this.metricFields = metricFields;
        return this;
    }

    public boolean isCollectIPAddresses() {
        return collectIPAddresses;
    }

    public ApplicationMetricsSchemaCDO setCollectIPAddresses(boolean collectIPAddresses) {
        this.collectIPAddresses = collectIPAddresses;
        return this;
    }
}
