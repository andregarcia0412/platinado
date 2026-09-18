package me.andregarcia0412.pipeline.modules.user.usecases;

import me.andregarcia0412.pipeline.modules.user.dtos.UpdateUserDto;
import me.andregarcia0412.pipeline.modules.user.entities.User;
import me.andregarcia0412.pipeline.modules.user.interfaces.IUserRepository;
import me.andregarcia0412.pipeline.shared.exception.exceptions.ConflictException;
import me.andregarcia0412.pipeline.shared.exception.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateUserByIdUseCase {
    private final IUserRepository userRepository;

    public UpdateUserByIdUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(Integer id, UpdateUserDto updateUserDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new NotFoundException("User not found");

        User entity = user.get();

        if(updateUserDto.name() != null) {
            entity.setName(updateUserDto.name());
        }

        if(updateUserDto.email() != null && !updateUserDto.email().equals(entity.getEmail())) {
            if(userRepository.existsByEmail(updateUserDto.email()))
                throw new ConflictException("Email already in use");

            entity.setEmail(updateUserDto.email());
        }

        return userRepository.save(entity);
    }
}
