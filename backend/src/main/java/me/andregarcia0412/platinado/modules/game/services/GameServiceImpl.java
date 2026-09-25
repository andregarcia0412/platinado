package me.andregarcia0412.platinado.modules.game.services;

import me.andregarcia0412.platinado.modules.game.dtos.CreateGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.ReturnGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.UpdateGameDto;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameService;
import me.andregarcia0412.platinado.modules.game.usecases.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class GameServiceImpl implements IGameService {
    private final CreateGameUseCase createGameUseCase;
    private final FindAllGamesUseCase findAllGamesUseCase;
    private final FindGameByIdUseCase findGameByIdUseCase;
    private final FindGameBySlugUseCase findGameBySlugUseCase;
    private final UpdateGameByIdUseCase updateGameByIdUseCase;
    private final DeleteGameByIdUseCase deleteGameByIdUseCase;
    private final CreateCoverImageUseCase createCoverImageUseCase;
    private final GetGameCoverUseCase getGameCoverUseCase;

    public GameServiceImpl(
            CreateGameUseCase createGameUseCase,
            FindAllGamesUseCase findAllGamesUseCase,
            FindGameByIdUseCase findGameByIdUseCase,
            FindGameBySlugUseCase findGameBySlugUseCase,
            UpdateGameByIdUseCase updateGameByIdUseCase,
            DeleteGameByIdUseCase deleteGameByIdUseCase,
            CreateCoverImageUseCase createCoverImageUseCase,
            GetGameCoverUseCase getGameCoverUseCase
    ) {
        this.createGameUseCase = createGameUseCase;
        this.findAllGamesUseCase = findAllGamesUseCase;
        this.findGameByIdUseCase = findGameByIdUseCase;
        this.findGameBySlugUseCase = findGameBySlugUseCase;
        this.updateGameByIdUseCase = updateGameByIdUseCase;
        this.deleteGameByIdUseCase = deleteGameByIdUseCase;
        this.createCoverImageUseCase = createCoverImageUseCase;
        this.getGameCoverUseCase = getGameCoverUseCase;
    }

    @Override
    public ReturnGameDto create(CreateGameDto createGameDto) {
        return ReturnGameDto.fromEntity(createGameUseCase.execute(createGameDto));
    }

    @Override
    public List<ReturnGameDto> findAll() {
        return findAllGamesUseCase.execute().stream().map(ReturnGameDto::fromEntity).toList();
    }

    @Override
    public ReturnGameDto findById(Integer id) {
        return ReturnGameDto.fromEntity(findGameByIdUseCase.execute(id));
    }

    @Override
    public ReturnGameDto findBySlug(String slug) {
        return ReturnGameDto.fromEntity(findGameBySlugUseCase.execute(slug));
    }

    @Override
    public ReturnGameDto updateById(Integer id, UpdateGameDto updateGameDto) {
        return ReturnGameDto.fromEntity(updateGameByIdUseCase.execute(id, updateGameDto));
    }

    @Override
    public void deleteById(Integer id) {
        deleteGameByIdUseCase.execute(id);
    }

    @Override
    public ReturnGameDto createCoverImage(Integer id, MultipartFile file) {
        return ReturnGameDto.fromEntity(createCoverImageUseCase.execute(id, file));
    }

    @Override
    public String getCoverImage(Integer id) {
        return getGameCoverUseCase.execute(id);
    }
}
