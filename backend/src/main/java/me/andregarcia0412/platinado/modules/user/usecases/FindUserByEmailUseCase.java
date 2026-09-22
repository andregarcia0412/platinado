package me.andregarcia0412.platinado.modules.user.usecases;

import me.andregarcia0412.platinado.modules.user.entities.User;
import me.andregarcia0412.platinado.modules.user.interfaces.IUserRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindUserByEmailUseCase {
    private final IUserRepository userRepository;

    public FindUserByEmailUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String email) {
        Optional<User> existing = userRepository.findByEmail(email);
        if(existing.isEmpty())
            throw new NotFoundException("User not found");

        return existing.get();
    }
}
