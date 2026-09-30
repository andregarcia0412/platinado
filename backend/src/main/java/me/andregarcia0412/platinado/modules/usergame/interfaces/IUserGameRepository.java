package me.andregarcia0412.platinado.modules.usergame.interfaces;

import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGameKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserGameRepository {
    UserGame save(UserGame userGame);
    Optional<UserGame> findById(UserGameKey id);
    Page<UserGame> findAllByUserId(Integer userId, Pageable pageable);
    Page<UserGame> findAllByUserIdAndGameCompletionStatusId(Integer userId, Integer gameCompletionStatusId, Pageable pageable);
    boolean existsById(UserGameKey id);
    void deleteById(UserGameKey id);
}
