package me.andregarcia0412.platinado.shared.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.andregarcia0412.platinado.shared.security.UserPrincipal;
import me.andregarcia0412.platinado.shared.security.exception.JWTGenerationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenProviderImpl implements ITokenProvider {
    @Value("${api.security.access.token.secret}")
    private String accessSecret;

    @Value("${api.security.refresh.token.secret}")
    private String refreshSecret;

    public String generateAccessToken(UserPrincipal user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(accessSecret);
            return JWT.create()
                    .withIssuer("platinado-api")
                    .withSubject(user.getUsername())
                    .withClaim("type", "access")
                    .withExpiresAt(Date.from(
                            Instant.now().plus(15, ChronoUnit.MINUTES)
                    ))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTGenerationException("Error while generating access token", exception);
        }
    }

    @Override
    public String generateRefreshToken(UserPrincipal user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(refreshSecret);
            return JWT.create()
                    .withIssuer("platinado-api")
                    .withSubject(user.getUsername())
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("type", "refresh")
                    .withExpiresAt(Date.from(
                            Instant.now().plus(7, ChronoUnit.DAYS)
                    ))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTGenerationException("Error while generating refresh token", exception);
        }
    }

    @Override
    public Optional<String> validateAccessToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(accessSecret);
            return Optional.of(JWT.require(algorithm)
                    .withIssuer("platinado-api")
                    .withClaim("type", "access")
                    .build()
                    .verify(token)
                    .getSubject());
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RefreshTokenClaims> validateRefreshToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(refreshSecret);
           DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("platinado-api")
                    .withClaim("type", "refresh")
                    .build()
                    .verify(token);

           return Optional.of(new RefreshTokenClaims(
                   jwt.getSubject(),
                   jwt.getId(),
                   jwt.getExpiresAtAsInstant()
           ));
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }
}
