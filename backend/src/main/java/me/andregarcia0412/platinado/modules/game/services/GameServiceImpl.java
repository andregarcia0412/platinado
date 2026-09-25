package me.andregarcia0412.platinado.modules.game.services;

import me.andregarcia0412.platinado.modules.game.dtos.CreateGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.ReturnGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.UpdateGameDto;
import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameService;
import me.andregarcia0412.platinado.modules.game.usecases.*;
import me.andregarcia0412.platinado.shared.provider.storage.IStorageProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;

@Service
public class GameServiceImpl implements IGameService {
    private static final Duration COVER_URL_TTL = Duration.ofMinutes(15);

    private final CreateGameUseCase createGameUseCase;
    private final FindAllGamesUseCase findAllGamesUseCase;
    private final FindGameByIdUseCase findGameByIdUseCase;
    private final FindGameBySlugUseCase findGameBySlugUseCase;
    private final UpdateGameByIdUseCase updateGameByIdUseCase;
    private final DeleteGameByIdUseCase deleteGameByIdUseCase;
    private final CreateCoverImageUseCase createCoverImageUseCase;
    private final GetGameCoverUseCase getGameCoverUseCase;
    private final IStorageProvider storageProvider;

    public GameServiceImpl(
            CreateGameUseCase createGameUseCase,
            FindAllGamesUseCase findAllGamesUseCase,
            FindGameByIdUseCase findGameByIdUseCase,
            FindGameBySlugUseCase findGameBySlugUseCase,
            UpdateGameByIdUseCase updateGameByIdUseCase,
            DeleteGameByIdUseCase deleteGameByIdUseCase,
            CreateCoverImageUseCase createCoverImageUseCase,
            GetGameCoverUseCase getGameCoverUseCase,
            IStorageProvider storageProvider
    ) {
        this.createGameUseCase = createGameUseCase;
        this.findAllGamesUseCase = findAllGamesUseCase;
        this.findGameByIdUseCase = findGameByIdUseCase;
        this.findGameBySlugUseCase = findGameBySlugUseCase;
        this.updateGameByIdUseCase = updateGameByIdUseCase;
        this.deleteGameByIdUseCase = deleteGameByIdUseCase;
        this.createCoverImageUseCase = createCoverImageUseCase;
        this.getGameCoverUseCase = getGameCoverUseCase;
        this.storageProvider = storageProvider;
    }

    @Override
    public ReturnGameDto create(CreateGameDto createGameDto) {
        return toDto(createGameUseCase.execute(createGameDto));
    }

    @Override
    public List<ReturnGameDto> findAll() {
        return findAllGamesUseCase.execute().stream().map(this::toDto).toList();
    }

    @Override
    public ReturnGameDto findById(Integer id) {
        return toDto(findGameByIdUseCase.execute(id));
    }

    @Override
    public ReturnGameDto findBySlug(String slug) {
        return toDto(findGameBySlugUseCase.execute(slug));
    }

    @Override
    public ReturnGameDto updateById(Integer id, UpdateGameDto updateGameDto) {
        return toDto(updateGameByIdUseCase.execute(id, updateGameDto));
    }

    @Override
    public void deleteById(Integer id) {
        deleteGameByIdUseCase.execute(id);
    }

    @Override
    public ReturnGameDto createCoverImage(Integer id, MultipartFile file) {
        return toDto(createCoverImageUseCase.execute(id, file));
    }

    @Override
    public String getCoverImage(Integer id) {
        return getGameCoverUseCase.execute(id);
    }

    private ReturnGameDto toDto(Game game) {
        String key = game.getCoverImageStorageKey();
        String coverUrl = key == null ? null : storageProvider.getPresignedUrl(key, COVER_URL_TTL);
        return ReturnGameDto.fromEntity(game, coverUrl);
    }
}
