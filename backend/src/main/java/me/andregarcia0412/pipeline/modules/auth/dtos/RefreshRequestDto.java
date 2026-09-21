package me.andregarcia0412.pipeline.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequestDto(
        @NotBlank(message = "accessToken can't be blank")
        String accessToken
) {
}
