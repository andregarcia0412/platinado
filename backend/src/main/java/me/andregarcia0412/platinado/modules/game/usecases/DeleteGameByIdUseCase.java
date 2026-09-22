package me.andregarcia0412.platinado.modules.game.usecases;

import me.andregarcia0412.platinado.modules.game.interfaces.IGameRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteGameByIdUseCase {
    private final IGameRepository gameRepository;

    public DeleteGameByIdUseCase(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void execute(Integer id) {
        this.gameRepository.deleteById(id);
    }
}
