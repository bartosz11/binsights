package one.bartosz.metrics.services;

import one.bartosz.metrics.exceptions.UserNotFoundException;
import one.bartosz.metrics.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username "+username+" couldn't be found."));
    }

    //todo
}
