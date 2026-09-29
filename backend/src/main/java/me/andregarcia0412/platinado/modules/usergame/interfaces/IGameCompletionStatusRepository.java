package me.andregarcia0412.platinado.modules.usergame.interfaces;

import me.andregarcia0412.platinado.modules.usergame.entities.GameCompletionStatus;

import java.util.List;
import java.util.Optional;

public interface IGameCompletionStatusRepository {
    Optional<GameCompletionStatus> findById(Integer id);
    List<GameCompletionStatus> findAll();
    Optional<GameCompletionStatus> existsByStatus(String status);
}
