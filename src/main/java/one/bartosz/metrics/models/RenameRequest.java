package one.bartosz.metrics.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RenameRequest {
    @NotNull
    @NotEmpty
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
