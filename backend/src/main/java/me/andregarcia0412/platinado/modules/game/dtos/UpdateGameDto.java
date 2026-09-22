package me.andregarcia0412.platinado.modules.game.dtos;

import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record UpdateGameDto(
        @Length(min = 1, max = 200, message = "name must be between 1 and 200 characters")
        String name,
        @Length(min = 1, max = 255, message = "slug must be between 1 and 255 characters")
        String slug,
        @Positive(message = "gameTypeId must be positive")
        Integer gameTypeId,
        String summary,
        LocalDateTime firstReleaseDate
) {
}
