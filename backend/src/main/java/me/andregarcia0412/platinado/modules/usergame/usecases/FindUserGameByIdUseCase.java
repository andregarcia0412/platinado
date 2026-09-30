package me.andregarcia0412.platinado.modules.usergame.usecases;

import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGameKey;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IUserGameRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class FindUserGameByIdUseCase {
    private final IUserGameRepository userGameRepository;

    public FindUserGameByIdUseCase(IUserGameRepository userGameRepository) {
        this.userGameRepository = userGameRepository;
    }

    public UserGame execute(Integer userId, Integer gameId) {
        return userGameRepository.findById(new UserGameKey(userId, gameId))
                .orElseThrow(() -> new NotFoundException("User game not found"));
    }
}
