package me.andregarcia0412.platinado.modules.user.usecases;

import me.andregarcia0412.platinado.modules.user.dtos.CreateUserDto;
import me.andregarcia0412.platinado.modules.user.entities.User;
import me.andregarcia0412.platinado.modules.user.interfaces.IUserRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.ConflictException;
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
            throw new ConflictException("Email already in use", "email");

        if(userRepository.existsByUsername(createUserDto.username()))
            throw new ConflictException("Username already in use", "username");

        return userRepository.save(
                new User(
                        createUserDto.username(),
                        createUserDto.email(),
                        passwordEncoder.encode(createUserDto.password())
                )
        );
    }
}
