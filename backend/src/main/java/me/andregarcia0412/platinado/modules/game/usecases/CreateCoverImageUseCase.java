package me.andregarcia0412.platinado.modules.game.usecases;

import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.BadRequestException;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import me.andregarcia0412.platinado.shared.provider.storage.IStorageProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

@Component
public class CreateCoverImageUseCase {
    private static final Set<String> ALLOWED_TYPES = Set.of("image/jpeg", "image/png", "image/webp");
    private static final long MAX_SIZE = 5 * 1024 * 1024;

    private final IGameRepository gameRepository;
    private final IStorageProvider storageProvider;

    public CreateCoverImageUseCase(IGameRepository gameRepository, IStorageProvider storageProvider) {
        this.gameRepository = gameRepository;
        this.storageProvider = storageProvider;
    }

    public Game execute(Integer id, MultipartFile file) {
        Optional<Game> existing = gameRepository.findById(id);

        if(existing.isEmpty())
            throw new NotFoundException("Game not found");

        if(file == null || file.isEmpty())
            throw new BadRequestException("File is required");

        if(!ALLOWED_TYPES.contains(file.getContentType()))
            throw new BadRequestException("Only JPEG, PNG, or WEBP images are allowed");

        if(file.getSize() > MAX_SIZE)
            throw new BadRequestException("File must be at most 5MB");

        Game game = existing.get();
        String oldKey = game.getCoverImageStorageKey();
        String newKey = storageProvider.upload(file, "games/" + id + "/cover");
        game.setCoverImageStorageKey(newKey);

        try {
            gameRepository.save(game);
        } catch (RuntimeException exception) {
            storageProvider.delete(newKey);
            throw exception;
        }

        if(oldKey != null)
            storageProvider.delete(oldKey);

        return game;
    }
}
