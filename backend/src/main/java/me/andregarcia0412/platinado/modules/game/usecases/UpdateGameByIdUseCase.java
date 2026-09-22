package me.andregarcia0412.platinado.modules.game.usecases;

import me.andregarcia0412.platinado.modules.game.dtos.UpdateGameDto;
import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.game.entities.GameType;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameRepository;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameTypeRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.ConflictException;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateGameByIdUseCase {
    private final IGameRepository gameRepository;
    private final IGameTypeRepository gameTypeRepository;

    public UpdateGameByIdUseCase(IGameRepository gameRepository, IGameTypeRepository gameTypeRepository) {
        this.gameRepository = gameRepository;
        this.gameTypeRepository = gameTypeRepository;
    }

    public Game execute(Integer id, UpdateGameDto updateGameDto) {
        Optional<Game> existing = gameRepository.findById(id);

        if(existing.isEmpty())
            throw new NotFoundException("Game not found");

        Game game = existing.get();

        if(updateGameDto.name() != null) {
            game.setName(updateGameDto.name());
        }

        if(updateGameDto.slug() != null && !updateGameDto.slug().equals(game.getSlug())) {
            if(gameRepository.existsBySlug(updateGameDto.slug()))
                throw new ConflictException("A game with this slug already exists");

            game.setSlug(updateGameDto.slug());
        }

        if(updateGameDto.firstReleaseDate() != null) {
            game.setFirstReleaseDate(updateGameDto.firstReleaseDate());
        }

        if(updateGameDto.summary() != null) {
            game.setSummary(updateGameDto.summary());
        }

        if(updateGameDto.gameTypeId() != null && !updateGameDto.gameTypeId().equals(game.getGameType().getId())) {
            Optional<GameType> gameType = gameTypeRepository.findById(updateGameDto.gameTypeId());

            if(gameType.isEmpty())
                throw new NotFoundException("Game Type not found");

            game.setGameType(gameType.get());
        }

        return gameRepository.save(game);
    }
}
