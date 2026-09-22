package me.andregarcia0412.platinado.modules.game.repositories;

import me.andregarcia0412.platinado.modules.game.entities.GameType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameTypeJpaRepository extends JpaRepository<GameType, Integer>{
    boolean existsByType(String type);
}
