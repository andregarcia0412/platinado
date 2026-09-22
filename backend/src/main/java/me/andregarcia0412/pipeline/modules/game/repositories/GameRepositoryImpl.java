package me.andregarcia0412.pipeline.modules.game.repositories;

import me.andregarcia0412.pipeline.modules.game.entities.Game;
import me.andregarcia0412.pipeline.modules.game.interfaces.IGameRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameRepositoryImpl implements IGameRepository {
    private final GameJpaRepository gameJpaRepository;

    public GameRepositoryImpl(GameJpaRepository gameJpaRepository) {
        this.gameJpaRepository = gameJpaRepository;
    }

    @Override
    public Game save(Game game) {
        return gameJpaRepository.save(game);
    }

    @Override
    public Optional<Game> findById(Integer id) {
        return gameJpaRepository.findById(id);
    }

    @Override
    public Optional<Game> findBySlug(String slug) {
        return gameJpaRepository.findBySlug(slug);
    }

    @Override
    public List<Game> findAll() {
        return gameJpaRepository.findAll();
    }

    @Override
    public boolean existsBySlug(String slug) {
        return gameJpaRepository.existsBySlug(slug);
    }

    @Override
    public void deleteById(Integer id) {
        gameJpaRepository.deleteById(id);
    }
}
