package me.andregarcia0412.platinado.modules.usergame.usecases;

import me.andregarcia0412.platinado.modules.usergame.dtos.UpdateUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.entities.GameCompletionStatus;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGameKey;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IGameCompletionStatusRepository;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IUserGameRepository;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserGameByIdUseCase {
    private final IUserGameRepository userGameRepository;
    private final IGameCompletionStatusRepository gameCompletionStatusRepository;

    public UpdateUserGameByIdUseCase(IUserGameRepository userGameRepository, IGameCompletionStatusRepository gameCompletionStatusRepository) {
        this.userGameRepository = userGameRepository;
        this.gameCompletionStatusRepository = gameCompletionStatusRepository;
    }

    public UserGame execute(Integer userId, Integer gameId, UpdateUserGameDto updateUserGameDto) {
        UserGame userGame = userGameRepository.findById(new UserGameKey(userId, gameId))
                .orElseThrow(() -> new NotFoundException("User game not found"));

        if(
                updateUserGameDto.gameCompletionStatusId() != null
                && !userGame.getGameCompletionStatus().getId().equals(updateUserGameDto.gameCompletionStatusId())
        ) {
            GameCompletionStatus gameCompletionStatus = gameCompletionStatusRepository
                    .findById(updateUserGameDto.gameCompletionStatusId())
                    .orElseThrow(() -> new NotFoundException("Game completion status not found"));

            userGame.setGameCompletionStatus(gameCompletionStatus);
        }

        if(updateUserGameDto.hoursPlayed() != null) {
            userGame.setHoursPlayed(updateUserGameDto.hoursPlayed());
        }

        if(updateUserGameDto.startingDate() != null) {
            userGame.setStartingDate(updateUserGameDto.startingDate());
        }

        if(updateUserGameDto.finishingDate() != null) {
            userGame.setFinishingDate(updateUserGameDto.finishingDate());
        }

        if(updateUserGameDto.grade() != null) {
            userGame.setGrade(updateUserGameDto.grade());
        }

        if(updateUserGameDto.note() != null) {
            userGame.setNote(updateUserGameDto.note());
        }

        return userGameRepository.save(userGame);
    }
}
