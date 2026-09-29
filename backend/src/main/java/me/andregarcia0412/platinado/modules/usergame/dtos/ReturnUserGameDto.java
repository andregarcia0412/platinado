package me.andregarcia0412.platinado.modules.usergame.dtos;

import me.andregarcia0412.platinado.modules.usergame.entities.GameCompletionStatus;
import me.andregarcia0412.platinado.modules.usergame.entities.UserGame;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReturnUserGameDto(
        GameCompletionStatus gameCompletionStatus,
        BigDecimal hoursPlayed,
        LocalDateTime startingDate,
        LocalDateTime finishingDate,
        BigDecimal grade,
        String note,
        LocalDateTime createdAt
) {
    public static ReturnUserGameDto fromEntity(UserGame userGame) {
        return new ReturnUserGameDto(
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
