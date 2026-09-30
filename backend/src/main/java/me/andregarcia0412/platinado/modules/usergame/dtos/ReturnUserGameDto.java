package me.andregarcia0412.platinado.modules.usergame.dtos;

import me.andregarcia0412.platinado.modules.game.dtos.ReturnGameDto;
import me.andregarcia0412.platinado.modules.usergame.entities.GameCompletionStatus;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReturnUserGameDto(
        Integer userId,
        ReturnGameDto game,
        GameCompletionStatus gameCompletionStatus,
        BigDecimal hoursPlayed,
        LocalDateTime startingDate,
        LocalDateTime finishingDate,
        BigDecimal grade,
        String note,
        LocalDateTime createdAt
) {
    public static ReturnUserGameDto fromEntity(UserGame userGame, String coverUrl) {
        return new ReturnUserGameDto(
                userGame.getId().getUserId(),
                ReturnGameDto.fromEntity(userGame.getGame(), coverUrl),
                userGame.getGameCompletionStatus(),
                userGame.getHoursPlayed(),
                userGame.getStartingDate(),
                userGame.getFinishingDate(),
                userGame.getGrade(),
                userGame.getNote(),
                userGame.getCreatedAt()
        );
    }
}
