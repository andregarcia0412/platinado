package me.andregarcia0412.platinado.modules.usergame.services;

import me.andregarcia0412.platinado.modules.usergame.dtos.CreateUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.dtos.ReturnUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.dtos.UpdateUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IUserGameService;
import me.andregarcia0412.platinado.modules.usergame.usecases.*;
import me.andregarcia0412.platinado.shared.dto.PageResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserGameServiceImpl implements IUserGameService {
    private final CreateUserGameUseCase createUserGameUseCase;
    private final FindAllUserGamesUseCase findAllUserGamesUseCase;
    private final FindUserGameByIdUseCase findUserGameByIdUseCase;
    private final UpdateUserGameByIdUseCase updateUserGameByIdUseCase;
    private final DeleteUserGameUseCase deleteUserGameUseCase;

    public UserGameServiceImpl(
            CreateUserGameUseCase createUserGameUseCase,
            FindAllUserGamesUseCase findAllUserGamesUseCase,
            FindUserGameByIdUseCase findUserGameByIdUseCase,
            UpdateUserGameByIdUseCase updateUserGameByIdUseCase,
            DeleteUserGameUseCase deleteUserGameUseCase
    ) {
        this.createUserGameUseCase = createUserGameUseCase;
        this.findAllUserGamesUseCase = findAllUserGamesUseCase;
        this.findUserGameByIdUseCase = findUserGameByIdUseCase;
        this.updateUserGameByIdUseCase = updateUserGameByIdUseCase;
        this.deleteUserGameUseCase = deleteUserGameUseCase;
    }

    @Override
    public ReturnUserGameDto create(Integer userId, CreateUserGameDto createUserGameDto) {
        return ReturnUserGameDto.fromEntity(createUserGameUseCase.execute(userId, createUserGameDto));
    }

    @Override
    public ReturnUserGameDto findById(Integer userId, Integer gameId) {
        return ReturnUserGameDto.fromEntity(findUserGameByIdUseCase.execute(userId, gameId));
    }

    @Override
    public PageResponseDto<ReturnUserGameDto> findAll(Integer userId, Integer gameCompletionStatusId, Pageable pageable) {
        return PageResponseDto.from(
                findAllUserGamesUseCase
                        .execute(userId, gameCompletionStatusId, pageable)
                        .map(ReturnUserGameDto::fromEntity)
        );
    }

    @Override
    public ReturnUserGameDto updateById(Integer userId, Integer gameId, UpdateUserGameDto updateUserGameDto) {
        return ReturnUserGameDto.fromEntity(updateUserGameByIdUseCase.execute(userId, gameId, updateUserGameDto));
    }

    @Override
    public void deleteById(Integer userId, Integer gameId) {
        deleteUserGameUseCase.execute(userId, gameId);
    }
}
