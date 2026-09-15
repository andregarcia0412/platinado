package me.andregarcia0412.pipeline.modules.user.services;

import me.andregarcia0412.pipeline.modules.user.dto.CreateUserDto;
import me.andregarcia0412.pipeline.modules.user.dto.ReturnUserDto;
import me.andregarcia0412.pipeline.modules.user.dto.UpdateUserDto;
import me.andregarcia0412.pipeline.modules.user.entities.User;
import me.andregarcia0412.pipeline.modules.user.repositories.UserRepository;
import me.andregarcia0412.pipeline.shared.exception.ConflictException;
import me.andregarcia0412.pipeline.shared.exception.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ReturnUserDto create(CreateUserDto createUserDto) {
        if(userRepository.existsByEmail(createUserDto.email())) {
            throw new ConflictException("Email already in use");
        }

        return ReturnUserDto.fromEntity(
                userRepository.save(
                        new User(
                                createUserDto.name(),
                                createUserDto.email(),
                                passwordEncoder.encode(createUserDto.password())
                        )
                )
        );
    }

    @Override
    public ReturnUserDto findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        return ReturnUserDto.fromEntity(user.get());
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public ReturnUserDto updateById(Integer id, UpdateUserDto updateUserDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        User entity = user.get();

        if(updateUserDto.name() != null) {
            entity.setName(updateUserDto.name());
        }

        if(updateUserDto.email() != null && !updateUserDto.email().equals(entity.getEmail())) {
            if(userRepository.existsByEmail(updateUserDto.email())) {
                throw new ConflictException("Email already in use");
            }

            entity.setEmail(updateUserDto.email());
        }

        return ReturnUserDto.fromEntity(userRepository.save(entity));
    }

    @Override
    public ReturnUserDto deleteById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        User deleted = user.get();
        userRepository.delete(deleted);
        return ReturnUserDto.fromEntity(deleted);
    }
}
