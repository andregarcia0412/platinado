package me.andregarcia0412.platinado.modules.game.interfaces;

import me.andregarcia0412.platinado.modules.game.entities.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGameRepository {
    Game save(Game game);
    Optional<Game> findById(Integer id);
    Optional<Game> findBySlug(String slug);
    Page<Game> findAll(Pageable pageable);
    Page<Game> findAllByGameTypeId(Integer gameTypeId, Pageable pageable);
    boolean existsBySlug(String slug);
    void deleteById(Integer id);
}
