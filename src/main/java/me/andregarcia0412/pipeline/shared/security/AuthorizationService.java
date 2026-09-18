package me.andregarcia0412.pipeline.shared.security;

import me.andregarcia0412.pipeline.modules.user.entities.User;
import me.andregarcia0412.pipeline.modules.user.interfaces.IUserRepository;
import me.andregarcia0412.pipeline.shared.exception.exceptions.NotFoundException;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final IUserRepository userRepository;

    public AuthorizationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
