package me.andregarcia0412.pipeline.modules.game.repositories;

import me.andregarcia0412.pipeline.modules.game.entities.GameType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameTypeJpaRepository extends JpaRepository<GameType, Integer>{
    boolean existsByType(String type);
}
