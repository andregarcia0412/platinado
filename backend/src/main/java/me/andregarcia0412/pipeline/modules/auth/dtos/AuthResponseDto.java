package me.andregarcia0412.pipeline.modules.auth.dto;

public record AuthResponseDto(
        String accessToken,
        String refreshToken
) {
}
