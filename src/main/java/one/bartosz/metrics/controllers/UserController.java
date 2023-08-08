package one.bartosz.metrics.controllers;

import jakarta.validation.Valid;
import one.bartosz.metrics.exceptions.InvalidPasswordException;
import one.bartosz.metrics.exceptions.UsernameAlreadyTakenException;
import one.bartosz.metrics.models.PasswordChangeRequest;
import one.bartosz.metrics.models.RenameRequest;
import one.bartosz.metrics.models.Response;
import one.bartosz.metrics.models.User;
import one.bartosz.metrics.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
//A controller for managing user's own account
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Response> getCurrentUser(@AuthenticationPrincipal User user) {
        return new Response(HttpStatus.OK).addAdditionalData(user).toResponseEntity();
    }

    @DeleteMapping("")
    @ResponseBody
    public ResponseEntity<Response> deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user);
        return new Response(HttpStatus.NO_CONTENT).toResponseEntity();
    }

    @PatchMapping("/username")
    @ResponseBody
    public ResponseEntity<Response> changeUsername(@AuthenticationPrincipal User user, @RequestBody @Valid RenameRequest renameRequest) throws UsernameAlreadyTakenException {
        User changedUsername = userService.changeUsername(user, renameRequest);
        return new Response(HttpStatus.OK).addAdditionalData(changedUsername).toResponseEntity();
    }

    @PatchMapping("/password")
    @ResponseBody
    public ResponseEntity<Response> changePassword(@AuthenticationPrincipal User user, @RequestBody @Valid PasswordChangeRequest passwordChangeRequest) throws InvalidPasswordException {
        User changedPassword = userService.changePassword(user, passwordChangeRequest);
        return new Response(HttpStatus.OK).addAdditionalData(changedPassword).toResponseEntity();
    }
}
