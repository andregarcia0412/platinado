package me.andregarcia0412.platinado.modules.game.usecases;

import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllGamesUseCase {
    private final IGameRepository gameRepository;

    public FindAllGamesUseCase(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> execute() {
        return gameRepository.findAll();
    }
}
