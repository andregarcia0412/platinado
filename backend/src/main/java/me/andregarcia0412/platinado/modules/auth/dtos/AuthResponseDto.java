package me.andregarcia0412.platinado.modules.auth.dtos;

public record AuthResponseDto(
        String accessToken,
        String refreshToken
) {
}
