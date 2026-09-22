package me.andregarcia0412.platinado.modules.game.usecases;

import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindGameByIdUseCase {
    private final IGameRepository gameRepository;

    public FindGameByIdUseCase(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game execute(Integer id) {
        Optional<Game> game = gameRepository.findById(id);

        if(game.isEmpty())
            throw new NotFoundException("Game not found");

        return game.get();
    }
}
