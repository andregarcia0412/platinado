package me.andregarcia0412.pipeline.modules.user.usecases;

import me.andregarcia0412.pipeline.modules.user.entities.User;
import me.andregarcia0412.pipeline.modules.user.interfaces.IUserRepository;
import me.andregarcia0412.pipeline.shared.exception.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindUserByUsernameUseCase {
    private final IUserRepository userRepository;

    public FindUserByUsernameUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty())
            throw new NotFoundException("User not found");

        return user.get();
    }
}
