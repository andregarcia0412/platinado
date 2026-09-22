package me.andregarcia0412.platinado.modules.game.repositories;

import me.andregarcia0412.platinado.modules.game.entities.GameType;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameTypeRepositoryImpl implements IGameTypeRepository {
    private final GameTypeJpaRepository gameTypeJpaRepository;

    public GameTypeRepositoryImpl(GameTypeJpaRepository gameTypeJpaRepository) {
        this.gameTypeJpaRepository = gameTypeJpaRepository;
    }

    @Override
    public Optional<GameType> findById(Integer id) {
        return gameTypeJpaRepository.findById(id);
    }

    @Override
    public List<GameType> findAll() {
        return gameTypeJpaRepository.findAll();
    }

    @Override
    public boolean existsByType(String type) {
        return gameTypeJpaRepository.existsByType(type);
    }
}
