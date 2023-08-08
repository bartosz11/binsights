package one.bartosz.metrics.models;

import jakarta.validation.constraints.Pattern;
import one.bartosz.metrics.services.UserService;

public class AuthRequest {

    private String username;
    //shouldn't be a problem since we enforce the validation rules using @Valid, so we can just not enforce them in /auth/login
    @Pattern(regexp = UserService.PASSWORD_VALIDATION_PATTERN)
    private String password;

    public String getUsername() {
        return username;
    }

    public AuthRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
