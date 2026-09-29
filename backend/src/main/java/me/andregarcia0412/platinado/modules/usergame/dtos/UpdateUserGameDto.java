package me.andregarcia0412.platinado.modules.usergame.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateUserGameDto(
        @Positive(message = "gameCompletionStatusId must be positive")
        Integer gameCompletionStatusId,
        @Positive(message = "hoursPlayed must be positive")
        BigDecimal hoursPlayed,
        LocalDateTime startingDate,
        LocalDateTime finishingDate,
        @PositiveOrZero(message = "grade must be positive or zero")
        @Max(value=5, message = "grade must be lower or equal 5")
        BigDecimal grade,
        @Length(max = 512, message = "note length can't be bigger than 512")
        String note
) {
}
