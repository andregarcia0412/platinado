package me.andregarcia0412.pipeline.modules.user.usecases;

import me.andregarcia0412.pipeline.modules.user.dtos.CreateUserDto;
import me.andregarcia0412.pipeline.modules.user.entities.User;
import me.andregarcia0412.pipeline.modules.user.interfaces.IUserRepository;
import me.andregarcia0412.pipeline.shared.exception.exceptions.ConflictException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCase {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(CreateUserDto createUserDto) {
        if(userRepository.existsByEmail(createUserDto.email()))
            throw new ConflictException("Email already in use");

        if(userRepository.existsByUsername(createUserDto.username()))
            throw new ConflictException("Username already in use");

        return userRepository.save(
                new User(
                        createUserDto.username(),
                        createUserDto.email(),
                        passwordEncoder.encode(createUserDto.password())
                )
        );
    }
}
