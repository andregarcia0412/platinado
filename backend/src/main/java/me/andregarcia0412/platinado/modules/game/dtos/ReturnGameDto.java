package me.andregarcia0412.platinado.modules.game.dtos;

import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.game.entities.GameType;

import java.time.LocalDateTime;

public record ReturnGameDto(
        Integer id,
        String name,
        String slug,
        String summary,
        LocalDateTime firstReleaseDate,
        String coverUrl,
        GameType gameType,
        LocalDateTime createdAt
) {
    public static ReturnGameDto fromEntity(Game game, String coverUrl) {
        return new ReturnGameDto(
                game.getId(),
                game.getName(),
                game.getSlug(),
                game.getSummary(),
                game.getFirstReleaseDate(),
                coverUrl,
                game.getGameType(),
                game.getCreatedAt()
        );
    }
}
