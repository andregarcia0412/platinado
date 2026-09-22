package me.andregarcia0412.platinado.modules.auth.dtos;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequestDto(
        @NotBlank(message = "refreshToken can't be blank")
        String refreshToken
) {
}
