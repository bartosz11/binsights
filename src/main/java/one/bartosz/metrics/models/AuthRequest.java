package one.bartosz.metrics.models;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import one.bartosz.metrics.services.UserService;

public class AuthRequest {
    
    @NotBlank(message = "Username cannot be blank.")
    private String username;
    //shouldn't be a problem since we enforce the validation rules using @Valid, so we can just not enforce them in /auth/login
    @Pattern(regexp = UserService.PASSWORD_VALIDATION_PATTERN, message="Password must consist of at least 8 characters, must contain at least: 1 uppercase letter, 1 lowercase letter, 1 digit and one special character.")
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
