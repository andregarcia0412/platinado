package me.andregarcia0412.platinado.modules.usergame.repositories;

import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
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
    public Optional<UserGame> findById(Integer id) {
        return userGameJpaRepository.findById(id);
    }

    @Override
    public Page<UserGame> findAll(Pageable pageable) {
        return userGameJpaRepository.findAll(pageable);
    }

    @Override
    public Page<UserGame> findAllByGameCompletionStatusId(Integer gameCompletionStatusId, Pageable pageable) {
        return userGameJpaRepository.findByGameCompletionStatus_Id(gameCompletionStatusId, pageable);
    }

    @Override
    public void deleteById(Integer id) {
        userGameJpaRepository.deleteById(id);
    }
}
