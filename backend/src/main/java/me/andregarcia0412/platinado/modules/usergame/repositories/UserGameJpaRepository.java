package me.andregarcia0412.platinado.modules.usergame.repositories;

import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGameKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGameJpaRepository extends JpaRepository<UserGame, UserGameKey> {
    @EntityGraph(attributePaths = {"game", "game.gameType", "gameCompletionStatus"})
    Page<UserGame> findByUser_Id(Integer userId, Pageable pageable);

    @EntityGraph(attributePaths = {"game", "game.gameType", "gameCompletionStatus"})
    Page<UserGame> findByUser_IdAndGameCompletionStatus_Id(Integer userId, Integer gameCompletionStatusId, Pageable pageable);
}
