package me.andregarcia0412.platinado.modules.usergame.services;

import me.andregarcia0412.platinado.modules.usergame.dtos.CreateUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.dtos.ReturnUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.dtos.UpdateUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IUserGameService;
import me.andregarcia0412.platinado.modules.usergame.usecases.*;
import me.andregarcia0412.platinado.shared.dto.PageResponseDto;
import me.andregarcia0412.platinado.shared.provider.storage.IStorageProvider;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserGameServiceImpl implements IUserGameService {
    private static final Duration COVER_URL_TTL = Duration.ofMinutes(15);

    private final CreateUserGameUseCase createUserGameUseCase;
    private final FindAllUserGamesUseCase findAllUserGamesUseCase;
    private final FindUserGameByIdUseCase findUserGameByIdUseCase;
    private final UpdateUserGameByIdUseCase updateUserGameByIdUseCase;
    private final DeleteUserGameUseCase deleteUserGameUseCase;
    private final IStorageProvider storageProvider;

    public UserGameServiceImpl(
            CreateUserGameUseCase createUserGameUseCase,
            FindAllUserGamesUseCase findAllUserGamesUseCase,
            FindUserGameByIdUseCase findUserGameByIdUseCase,
            UpdateUserGameByIdUseCase updateUserGameByIdUseCase,
            DeleteUserGameUseCase deleteUserGameUseCase,
            IStorageProvider storageProvider
    ) {
        this.createUserGameUseCase = createUserGameUseCase;
        this.findAllUserGamesUseCase = findAllUserGamesUseCase;
        this.findUserGameByIdUseCase = findUserGameByIdUseCase;
        this.updateUserGameByIdUseCase = updateUserGameByIdUseCase;
        this.deleteUserGameUseCase = deleteUserGameUseCase;
        this.storageProvider = storageProvider;
    }

    @Override
    public ReturnUserGameDto create(Integer userId, CreateUserGameDto createUserGameDto) {
        return toDto(createUserGameUseCase.execute(userId, createUserGameDto));
    }

    @Override
    public ReturnUserGameDto findById(Integer userId, Integer gameId) {
        return toDto(findUserGameByIdUseCase.execute(userId, gameId));
    }

    @Override
    public PageResponseDto<ReturnUserGameDto> findAll(Integer userId, Integer gameCompletionStatusId, Pageable pageable) {
        return PageResponseDto.from(
                findAllUserGamesUseCase
                        .execute(userId, gameCompletionStatusId, pageable)
                        .map(this::toDto)
        );
    }

    @Override
    public ReturnUserGameDto updateById(Integer userId, Integer gameId, UpdateUserGameDto updateUserGameDto) {
        return toDto(updateUserGameByIdUseCase.execute(userId, gameId, updateUserGameDto));
    }

    @Override
    public void deleteById(Integer userId, Integer gameId) {
        deleteUserGameUseCase.execute(userId, gameId);
    }

    private ReturnUserGameDto toDto(UserGame userGame) {
        String key = userGame.getGame().getCoverImageStorageKey();
        String coverUrl = key == null ? null : storageProvider.getPresignedUrl(key, COVER_URL_TTL);
        return ReturnUserGameDto.fromEntity(userGame, coverUrl);
    }
}
