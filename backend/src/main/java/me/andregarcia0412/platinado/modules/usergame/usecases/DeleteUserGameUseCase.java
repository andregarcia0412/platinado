package me.andregarcia0412.platinado.modules.usergame.usecases;

import me.andregarcia0412.platinado.modules.usergame.entities.UserGameKey;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IUserGameRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserGameUseCase {
    private final IUserGameRepository userGameRepository;

    public DeleteUserGameUseCase(IUserGameRepository userGameRepository) {
        this.userGameRepository = userGameRepository;
    }

    public void execute(Integer userId, Integer gameId) {
       userGameRepository.deleteById(new UserGameKey(userId, gameId));
    }
}
