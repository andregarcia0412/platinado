package me.andregarcia0412.platinado.shared.security.provider;

import java.time.Instant;

public record RefreshTokenClaims(
        String subject,
        String tokenId,
        Instant expiresAt
) {
}
