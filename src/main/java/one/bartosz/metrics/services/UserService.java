package one.bartosz.metrics.services;

import one.bartosz.metrics.exceptions.InvalidInviteCodeException;
import one.bartosz.metrics.exceptions.InvalidPasswordException;
import one.bartosz.metrics.exceptions.UserNotFoundException;
import one.bartosz.metrics.exceptions.UsernameAlreadyTakenException;
import one.bartosz.metrics.models.AuthRequest;
import one.bartosz.metrics.models.InviteCode;
import one.bartosz.metrics.models.User;
import one.bartosz.metrics.repositories.InviteCodeRepository;
import one.bartosz.metrics.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final InviteCodeRepository inviteCodeRepository;
    private final PasswordEncoder passwordEncoder;

    //Copied from regexr.com/3bfsi
    private final String PASSWORD_VALIDATION_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

    public UserService(UserRepository userRepository, InviteCodeRepository inviteCodeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.inviteCodeRepository = inviteCodeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username "+username+" couldn't be found."));
    }

    public User createNewUser(AuthRequest authRequest, String inviteCode) throws InvalidInviteCodeException, UsernameAlreadyTakenException, InvalidPasswordException {
        if ((userRepository.count() != 0) && !validateInviteCode(inviteCode)) throw new InvalidInviteCodeException("Given invite code is invalid.");
        String username = authRequest.getUsername();
        String passwordRaw = authRequest.getPassword();
        if (userRepository.existsByUsername(username)) throw new UsernameAlreadyTakenException("Given username is already taken.");
        if (!passwordRaw.matches(PASSWORD_VALIDATION_PATTERN)) throw new InvalidPasswordException("Invalid password. A valid password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number and must consist of at least 8 characters.");
        //it's meant to be a one time use code
        if (inviteCode != null) inviteCodeRepository.deleteById(inviteCode);
        return userRepository.save(new User().setUsername(username).setPassword(passwordEncoder.encode(passwordRaw)).setEnabled(true).setLastUpdated(Instant.now().toEpochMilli()));
    }

    private boolean validateInviteCode(String inviteCode) {
        if (inviteCode == null) return false;
        Optional<InviteCode> byId = inviteCodeRepository.findById(inviteCode);
        if (byId.isEmpty()) return false;
        InviteCode invite = byId.get();
        if (!(invite.getExpiresOn() > Instant.now().getEpochSecond())) {
            inviteCodeRepository.delete(invite);
            return false;
        }
        return true;
    }

}
