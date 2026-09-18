package me.andregarcia0412.pipeline.modules.user.usecases;

import me.andregarcia0412.pipeline.modules.user.interfaces.IUserRepository;
import me.andregarcia0412.pipeline.shared.exception.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserUseCase {
    private final IUserRepository userRepository;

    public DeleteUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Integer id) {
        if(!userRepository.existsById(id))
            throw new NotFoundException("User not found");

        userRepository.deleteById(id);
    }
}
