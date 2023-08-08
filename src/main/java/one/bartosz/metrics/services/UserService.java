package one.bartosz.metrics.services;

import jakarta.transaction.Transactional;
import one.bartosz.metrics.exceptions.InvalidInviteCodeException;
import one.bartosz.metrics.exceptions.InvalidPasswordException;
import one.bartosz.metrics.exceptions.UserNotFoundException;
import one.bartosz.metrics.exceptions.UsernameAlreadyTakenException;
import one.bartosz.metrics.models.*;
import one.bartosz.metrics.repositories.InviteCodeRepository;
import one.bartosz.metrics.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final InviteCodeRepository inviteCodeRepository;
    private final PasswordEncoder passwordEncoder;

    //Copied from https://regexr.com/3bfsi
    public static final String PASSWORD_VALIDATION_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

    public UserService(UserRepository userRepository, InviteCodeRepository inviteCodeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.inviteCodeRepository = inviteCodeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " couldn't be found."));
    }

    public User createNewUser(AuthRequest authRequest, String inviteCode) throws InvalidInviteCodeException, UsernameAlreadyTakenException {
        if ((userRepository.count() != 0) && !validateInviteCode(inviteCode))
            throw new InvalidInviteCodeException("Given invite code is invalid.");
        String username = authRequest.getUsername();
        String passwordRaw = authRequest.getPassword();
        if (userRepository.existsByUsername(username))
            throw new UsernameAlreadyTakenException("Given username is already taken.");
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

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User changeUsername(User user, RenameRequest renameRequest) throws UsernameAlreadyTakenException {
        String newName = renameRequest.getName();
        if (userRepository.existsByUsername(newName))
            throw new UsernameAlreadyTakenException("Given username is already taken.");
        return userRepository.save(user.setUsername(newName).setLastUpdated(Instant.now().toEpochMilli()));
    }

    public User changePassword(User user, PasswordChangeRequest passwordChangeRequest) throws InvalidPasswordException {
        String oldPassword = passwordChangeRequest.getOldPassword();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) throw new InvalidPasswordException("Old password is invalid.");
        String newPassword = passwordChangeRequest.getNewPassword();
        String confirmNewPassword = passwordChangeRequest.getConfirmNewPassword();
        if (!newPassword.equals(confirmNewPassword)) throw new InvalidPasswordException("New passwords don't match.");
        return userRepository.save(user.setPassword(passwordEncoder.encode(newPassword)).setLastUpdated(Instant.now().toEpochMilli()));
    }
}
