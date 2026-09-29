package me.andregarcia0412.platinado.modules.usergame.interfaces;

import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserGameRepository {
    UserGame save(UserGame userGame);
    Optional<UserGame> findById(Integer id);
    Page<UserGame> findAll(Pageable pageable);
    Page<UserGame> findAllByGameCompletionStatusId(Integer gameCompletionStatusId, Pageable pageable);
    void deleteById(Integer id);
}
