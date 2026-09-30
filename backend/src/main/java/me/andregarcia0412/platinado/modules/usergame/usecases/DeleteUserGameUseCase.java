package me.andregarcia0412.platinado.modules.usergame.usecases;

import me.andregarcia0412.platinado.modules.usergame.interfaces.IUserGameRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserGameUseCase {
    private final IUserGameRepository userGameRepository;

    public DeleteUserGameUseCase(IUserGameRepository userGameRepository) {
        this.userGameRepository = userGameRepository;
    }

    public void execute(Integer id) {
       userGameRepository.deleteById(id);
    }
}
