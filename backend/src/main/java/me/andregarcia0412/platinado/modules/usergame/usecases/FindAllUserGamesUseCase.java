package me.andregarcia0412.platinado.modules.usergame.usecases;

import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IUserGameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FindAllUserGamesUseCase {
    private final IUserGameRepository userGameRepository;

    public FindAllUserGamesUseCase(IUserGameRepository userGameRepository) {
        this.userGameRepository = userGameRepository;
    }

    public Page<UserGame> execute(Integer gameCompletionStatusId, Pageable pageable) {
        if(gameCompletionStatusId != null)
            return userGameRepository.findAllByGameCompletionStatusId(gameCompletionStatusId, pageable);

        return userGameRepository.findAll(pageable);
    }
}
