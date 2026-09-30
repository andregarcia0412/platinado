package me.andregarcia0412.platinado.modules.usergame.repositories;

import me.andregarcia0412.platinado.modules.usergame.entities.GameCompletionStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameCompletionStatusJpaRepository extends JpaRepository<GameCompletionStatus, Integer> {
    boolean existsByStatus(String status);
}
