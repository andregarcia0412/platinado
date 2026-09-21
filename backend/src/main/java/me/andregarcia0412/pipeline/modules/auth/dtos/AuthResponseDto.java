package me.andregarcia0412.pipeline.modules.auth.dtos;

public record AuthResponseDto(
        String accessToken,
        String refreshToken
) {
}
