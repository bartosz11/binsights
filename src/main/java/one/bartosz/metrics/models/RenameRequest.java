package one.bartosz.metrics.models;

import jakarta.validation.constraints.NotBlank;

public class RenameRequest {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public RenameRequest setName(String name) {
        this.name = name;
        return this;
    }
}
