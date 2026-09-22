package me.andregarcia0412.platinado.modules.user.usecases;

import me.andregarcia0412.platinado.modules.user.interfaces.IUserRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
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
