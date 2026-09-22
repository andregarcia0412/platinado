package me.andregarcia0412.pipeline.modules.game.repositories;

import me.andregarcia0412.pipeline.modules.game.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameJpaRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
