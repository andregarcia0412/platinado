package me.andregarcia0412.platinado.modules.game.usecases;

import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import me.andregarcia0412.platinado.shared.provider.storage.IStorageProvider;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class GetGameCoverUseCase {
    private IGameRepository gameRepository;
    private IStorageProvider storageProvider;

    public GetGameCoverUseCase(IGameRepository gameRepository, IStorageProvider storageProvider) {
        this.gameRepository = gameRepository;
        this.storageProvider = storageProvider;
    }

    public String execute(Integer id) {
        Optional<Game> existing = gameRepository.findById(id);

        if(existing.isEmpty())
            throw new NotFoundException("Game not found");

        Game game = existing.get();

        if(game.getCoverImageStorageKey() == null)
            throw new NotFoundException("Game cover not found");

        return storageProvider.getPresignedUrl(
                game.getCoverImageStorageKey(),
                Duration.ofMinutes(15)
        );
    }
}
