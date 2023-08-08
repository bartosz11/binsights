package one.bartosz.metrics.models;

import jakarta.validation.constraints.Pattern;
import one.bartosz.metrics.services.UserService;

public class PasswordChangeRequest {

    //we don't validate old password - if user set it to something different for example using SQL we shouldn't prevent him from changing the password via API because of his former action
    private String oldPassword;
    @Pattern(regexp = UserService.PASSWORD_VALIDATION_PATTERN)
    private String newPassword;
    @Pattern(regexp = UserService.PASSWORD_VALIDATION_PATTERN)
    private String confirmNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public PasswordChangeRequest setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public PasswordChangeRequest setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public PasswordChangeRequest setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}
