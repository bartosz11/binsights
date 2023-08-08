package one.bartosz.metrics.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import one.bartosz.metrics.exceptions.InvalidCredentialsException;
import one.bartosz.metrics.exceptions.InvalidInviteCodeException;
import one.bartosz.metrics.exceptions.UserDisabledException;
import one.bartosz.metrics.exceptions.UsernameAlreadyTakenException;
import one.bartosz.metrics.models.AuthRequest;
import one.bartosz.metrics.models.Response;
import one.bartosz.metrics.models.User;
import one.bartosz.metrics.security.JWTTokenUtils;
import one.bartosz.metrics.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenUtils jwtTokenUtils;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JWTTokenUtils jwtTokenUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Response> login(@RequestBody AuthRequest authRequest, HttpServletResponse httpResponse) throws InvalidCredentialsException, UserDisabledException {
        User user = (User) userService.loadUserByUsername(authRequest.getUsername());
        authenticate(authRequest);
        String s = jwtTokenUtils.generateToken(user);
        //todo implement cookies here later
        return new Response(HttpStatus.OK).addAdditionalField("token", s).toResponseEntity();
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Response> register(@RequestBody @Valid AuthRequest authRequest, @RequestParam(required = false) String invite) throws InvalidInviteCodeException, UsernameAlreadyTakenException {
        User newUser = userService.createNewUser(authRequest, invite);
        return new Response(HttpStatus.CREATED).addAdditionalData(newUser).toResponseEntity();
    }

    private void authenticate(AuthRequest authRequest) throws InvalidCredentialsException, UserDisabledException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (DisabledException e) {
            throw new UserDisabledException("Account with username " + authRequest.getUsername() + " is disabled.");
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }
    }

}
