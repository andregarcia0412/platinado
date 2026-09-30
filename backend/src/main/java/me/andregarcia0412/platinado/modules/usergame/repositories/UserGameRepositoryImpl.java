package me.andregarcia0412.platinado.modules.usergame.repositories;

import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGameKey;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IUserGameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserGameRepositoryImpl implements IUserGameRepository {
    private final UserGameJpaRepository userGameJpaRepository;

    public UserGameRepositoryImpl(UserGameJpaRepository userGameJpaRepository) {
        this.userGameJpaRepository = userGameJpaRepository;
    }

    @Override
    public UserGame save(UserGame userGame) {
        return userGameJpaRepository.save(userGame);
    }

    @Override
    public Optional<UserGame> findById(UserGameKey id) {
        return userGameJpaRepository.findById(id);
    }

    @Override
    public Page<UserGame> findAllByUserId(Integer userId, Pageable pageable) {
        return userGameJpaRepository.findByUser_Id(userId, pageable);
    }

    @Override
    public Page<UserGame> findAllByUserIdAndGameCompletionStatusId(Integer userId, Integer gameCompletionStatusId, Pageable pageable) {
        return userGameJpaRepository.findByUser_IdAndGameCompletionStatus_Id(userId, gameCompletionStatusId, pageable);
    }

    @Override
    public boolean existsById(UserGameKey id) {
        return userGameJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(UserGameKey id) {
        userGameJpaRepository.deleteById(id);
    }
}
