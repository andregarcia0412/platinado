package me.andregarcia0412.platinado.modules.user.services;

import me.andregarcia0412.platinado.modules.user.dtos.CreateUserDto;
import me.andregarcia0412.platinado.modules.user.dtos.ReturnUserDto;
import me.andregarcia0412.platinado.modules.user.dtos.UpdateUserDto;
import me.andregarcia0412.platinado.modules.user.entities.User;
import me.andregarcia0412.platinado.modules.user.interfaces.IUserService;
import me.andregarcia0412.platinado.modules.user.usecases.*;
import me.andregarcia0412.platinado.shared.observer.IUserObserver;
import me.andregarcia0412.platinado.shared.observer.LogUserObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final FindUserByUsernameUseCase findUserByUsernameUseCase;
    private final UpdateUserByIdUseCase updateUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final List<IUserObserver> userObservers = new ArrayList<IUserObserver>();

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
        this.userObservers.add(new LogUserObserver());
    }

    @Override
    public User create(CreateUserDto createUserDto) {
        User user = createUserUseCase.execute(createUserDto);
        for(IUserObserver observer : userObservers) {
            observer.onUserCreated(user);
        }

        return user;
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
        User user = updateUserByIdUseCase.execute(id, updateUserDto);
        for(IUserObserver observer : userObservers) {
            observer.onUserUpdated(user);
        }
        return ReturnUserDto.fromEntity(user);
    }

    @Override
    public void deleteById(Integer id) {
        deleteUserUseCase.execute(id);
        for(IUserObserver observer : userObservers) {
            observer.onUserDeleted(id);
        }
    }
}
