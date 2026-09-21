package me.andregarcia0412.pipeline.shared.security.provider;

import java.time.Instant;

public record RefreshTokenClaims(
        String subject,
        String tokenId,
        Instant expiresAt
) {
}
