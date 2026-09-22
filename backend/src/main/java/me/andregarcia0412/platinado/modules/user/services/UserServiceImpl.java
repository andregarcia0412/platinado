package me.andregarcia0412.pipeline.modules.user.services;

import me.andregarcia0412.pipeline.modules.user.dtos.CreateUserDto;
import me.andregarcia0412.pipeline.modules.user.dtos.ReturnUserDto;
import me.andregarcia0412.pipeline.modules.user.dtos.UpdateUserDto;
import me.andregarcia0412.pipeline.modules.user.entities.User;
import me.andregarcia0412.pipeline.modules.user.interfaces.IUserService;
import me.andregarcia0412.pipeline.modules.user.usecases.*;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final FindUserByUsernameUseCase findUserByUsernameUseCase;
    private final UpdateUserByIdUseCase updateUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserServiceImpl(
            CreateUserUseCase createUserUseCase,
            FindUserByIdUseCase findUserByIdUseCase,
            FindUserByEmailUseCase findUserByEmailUseCase,
            FindUserByUsernameUseCase findUserByUsernameUseCase,
            UpdateUserByIdUseCase updateUserByIdUseCase,
            DeleteUserUseCase deleteUserUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.findUserByEmailUseCase = findUserByEmailUseCase;
        this.findUserByUsernameUseCase = findUserByUsernameUseCase;
        this.updateUserByIdUseCase = updateUserByIdUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @Override
    public User create(CreateUserDto createUserDto) {
        return createUserUseCase.execute(createUserDto);
    }

    @Override
    public ReturnUserDto findById(Integer id) {
        return ReturnUserDto.fromEntity(findUserByIdUseCase.execute(id));
    }

    @Override
    public User findByEmail(String email) {
        return findUserByEmailUseCase.execute(email);
    }

    @Override
    public User findByUsername(String username) {
        return findUserByUsernameUseCase.execute(username);
    }

    @Override
    public ReturnUserDto updateById(Integer id, UpdateUserDto updateUserDto) {
        return ReturnUserDto.fromEntity(updateUserByIdUseCase.execute(id, updateUserDto));
    }

    @Override
    public void deleteById(Integer id) {
        deleteUserUseCase.execute(id);
    }
}
