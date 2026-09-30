package me.andregarcia0412.platinado.modules.usergame.usecases;

import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameRepository;
import me.andregarcia0412.platinado.modules.user.entities.User;
import me.andregarcia0412.platinado.modules.user.interfaces.IUserRepository;
import me.andregarcia0412.platinado.modules.usergame.dtos.CreateUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.entities.GameCompletionStatus;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGameKey;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IGameCompletionStatusRepository;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IUserGameRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.ConflictException;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CreateUserGameUseCase {
    private final IUserGameRepository userGameRepository;
    private final IUserRepository userRepository;
    private final IGameRepository gameRepository;
    private final IGameCompletionStatusRepository gameCompletionStatusRepository;

    public CreateUserGameUseCase(IUserGameRepository userGameRepository, IUserRepository userRepository, IGameRepository gameRepository, IGameCompletionStatusRepository gameCompletionStatusRepository) {
        this.userGameRepository = userGameRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameCompletionStatusRepository = gameCompletionStatusRepository;
    }

    public UserGame execute(Integer userId, CreateUserGameDto createUserGameDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Game game = gameRepository.findById(createUserGameDto.gameId())
                .orElseThrow(() -> new NotFoundException("Game not found"));

        if(userGameRepository.existsById(new UserGameKey(user.getId(), game.getId())))
            throw new ConflictException("Game already added to user's library", "gameId");

        GameCompletionStatus gameCompletionStatus = gameCompletionStatusRepository.findById(createUserGameDto.gameCompletionStatusId())
                .orElseThrow(() -> new NotFoundException("Game completion status not found"));

        UserGame userGame = UserGame.builder(user, game, gameCompletionStatus)
                .hoursPlayed(createUserGameDto.hoursPlayed())
                .startingDate(createUserGameDto.startingDate())
                .finishingDate(createUserGameDto.finishingDate())
                .grade(createUserGameDto.grade())
                .note(createUserGameDto.note())
                .build();

        return userGameRepository.save(userGame);
    }
}
