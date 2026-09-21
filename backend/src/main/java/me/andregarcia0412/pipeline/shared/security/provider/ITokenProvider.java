package me.andregarcia0412.pipeline.shared.security.provider;

import me.andregarcia0412.pipeline.modules.user.entities.User;

import java.util.Optional;

public interface ITokenProvider {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    Optional<String> validateAccessToken(String token);
    Optional<String> validateRefreshToken(String token);
}
