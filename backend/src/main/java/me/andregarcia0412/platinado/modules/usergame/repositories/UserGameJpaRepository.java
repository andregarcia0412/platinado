package me.andregarcia0412.platinado.modules.usergame.repositories;

import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGameJpaRepository extends JpaRepository<UserGame, Integer> {
    Page<UserGame> findByGameCompletionStatus_Id(Integer gameCompletionStatusId, Pageable pageable);
}
