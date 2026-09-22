package me.andregarcia0412.platinado.modules.game.interfaces;

import me.andregarcia0412.platinado.modules.game.entities.GameType;

import java.util.List;
import java.util.Optional;

public interface IGameTypeRepository {
    Optional<GameType> findById(Integer id);
    List<GameType> findAll();
    boolean existsByType(String type);
}
