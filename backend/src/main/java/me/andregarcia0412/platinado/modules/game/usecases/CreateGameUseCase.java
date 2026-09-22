package me.andregarcia0412.platinado.modules.game.usecases;

import me.andregarcia0412.platinado.modules.game.dtos.CreateGameDto;
import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.game.entities.GameType;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameRepository;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameTypeRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.ConflictException;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateGameUseCase {
    private final IGameRepository gameRepository;
    private final IGameTypeRepository gameTypeRepository;

    public CreateGameUseCase(IGameRepository gameRepository, IGameTypeRepository gameTypeRepository) {
        this.gameRepository = gameRepository;
        this.gameTypeRepository = gameTypeRepository;
    }

    public Game execute(CreateGameDto createGameDto) {
        Optional<GameType> gameType = gameTypeRepository.findById(createGameDto.gameTypeId());

        if(gameType.isEmpty())
            throw new NotFoundException("Game Type not found");

        if(gameRepository.existsBySlug(createGameDto.slug()))
            throw new ConflictException("A game with this slug already exists");

        return gameRepository.save(
                new Game(
                        createGameDto.name(),
                        createGameDto.slug(),
                        createGameDto.summary(),
                        createGameDto.firstReleaseDate(),
                        gameType.get()
                )
        );
    }
}
