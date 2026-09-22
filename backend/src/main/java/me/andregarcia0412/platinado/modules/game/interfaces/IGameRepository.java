package me.andregarcia0412.platinado.modules.game.interfaces;

import me.andregarcia0412.platinado.modules.game.entities.Game;

import java.util.List;
import java.util.Optional;

public interface IGameRepository {
    Game save(Game game);
    Optional<Game> findById(Integer id);
    Optional<Game> findBySlug(String slug);
    List<Game> findAll();
    boolean existsBySlug(String slug);
    void deleteById(Integer id);
}
