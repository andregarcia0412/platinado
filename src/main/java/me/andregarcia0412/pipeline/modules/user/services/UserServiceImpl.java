package me.andregarcia0412.pipeline.modules.user.services;

import me.andregarcia0412.pipeline.modules.user.dto.CreateUserDto;
import me.andregarcia0412.pipeline.modules.user.dto.ReturnUserDto;
import me.andregarcia0412.pipeline.modules.user.dto.UpdateUserDto;
import me.andregarcia0412.pipeline.modules.user.entities.User;
import me.andregarcia0412.pipeline.modules.user.repositories.UserRepository;
import me.andregarcia0412.pipeline.shared.exception.ConflictException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public ReturnUserDto updateById(Integer id, UpdateUserDto updateUserDto) {
        return null;
    }

    @Override
    public ReturnUserDto deleteById(Integer id) {
        return null;
    }
}
