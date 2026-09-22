package me.andregarcia0412.platinado.shared.security.provider;

import me.andregarcia0412.platinado.shared.security.UserPrincipal;

import java.util.Optional;

public interface ITokenProvider {
    String generateAccessToken(UserPrincipal user);
    String generateRefreshToken(UserPrincipal user);
    Optional<String> validateAccessToken(String token);
    Optional<RefreshTokenClaims> validateRefreshToken(String token);
}
