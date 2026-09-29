package me.andregarcia0412.platinado.modules.usergame.repositories;

import me.andregarcia0412.platinado.modules.usergame.entities.GameCompletionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameCompletionStatusJpaRepository extends JpaRepository<GameCompletionStatus, Integer> {
    Optional<GameCompletionStatus> existsByStatus(String status);
}
