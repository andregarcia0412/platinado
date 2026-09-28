package me.andregarcia0412.platinado.modules.game.usecases;

import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FindAllGamesUseCase {
    private final IGameRepository gameRepository;

    public FindAllGamesUseCase(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Page<Game> execute(Integer gameTypeId, Pageable pageable) {
        if(gameTypeId != null) {
            return gameRepository.findAllByGameTypeId(gameTypeId, pageable);
        }
        return gameRepository.findAll(pageable);
    }
}
