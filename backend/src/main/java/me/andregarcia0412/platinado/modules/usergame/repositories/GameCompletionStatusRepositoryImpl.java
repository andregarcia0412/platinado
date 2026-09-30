package me.andregarcia0412.platinado.modules.usergame.repositories;

import me.andregarcia0412.platinado.modules.usergame.entities.GameCompletionStatus;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IGameCompletionStatusRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameCompletionStatusRepositoryImpl implements IGameCompletionStatusRepository {
    private final GameCompletionStatusJpaRepository gameCompletionStatusJpaRepository;

    public GameCompletionStatusRepositoryImpl(GameCompletionStatusJpaRepository gameCompletionStatusJpaRepository) {
        this.gameCompletionStatusJpaRepository = gameCompletionStatusJpaRepository;
    }

    @Override
    public Optional<GameCompletionStatus> findById(Integer id) {
        return gameCompletionStatusJpaRepository.findById(id);
    }

    @Override
    public List<GameCompletionStatus> findAll() {
        return gameCompletionStatusJpaRepository.findAll();
    }

    @Override
    public boolean existsByStatus(String status) {
        return gameCompletionStatusJpaRepository.existsByStatus(status);
    }
}
