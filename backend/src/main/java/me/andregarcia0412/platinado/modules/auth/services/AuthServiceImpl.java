package me.andregarcia0412.platinado.modules.auth.services;

import me.andregarcia0412.platinado.modules.auth.dtos.AuthResponseDto;
import me.andregarcia0412.platinado.modules.auth.dtos.LoginRequestDto;
import me.andregarcia0412.platinado.modules.auth.dtos.RefreshRequestDto;
import me.andregarcia0412.platinado.modules.auth.dtos.RegisterRequestDto;
import me.andregarcia0412.platinado.modules.auth.interfaces.IAuthService;
import me.andregarcia0412.platinado.modules.user.dtos.CreateUserDto;
import me.andregarcia0412.platinado.modules.user.entities.User;
import me.andregarcia0412.platinado.modules.user.interfaces.IUserService;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import me.andregarcia0412.platinado.shared.exception.exceptions.UnauthorizedException;
import me.andregarcia0412.platinado.shared.provider.cache.ICacheProvider;
import me.andregarcia0412.platinado.shared.security.UserPrincipal;
import me.andregarcia0412.platinado.shared.security.provider.ITokenProvider;
import me.andregarcia0412.platinado.shared.security.provider.RefreshTokenClaims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class AuthServiceImpl implements IAuthService {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final ITokenProvider tokenProvider;
    private final ICacheProvider cacheProvider;
    private static final String REFRESH_BLACKLIST_PREFIX = "auth:refresh:blacklist:";

    public AuthServiceImpl(
            IUserService userService,
            AuthenticationManager authenticationManager,
            ITokenProvider tokenProvider,
            ICacheProvider cacheProvider
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.cacheProvider = cacheProvider;
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.username(),
                loginRequestDto.password()
        );
        var auth = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserPrincipal user = (UserPrincipal) auth.getPrincipal();

        return new AuthResponseDto(
                tokenProvider.generateAccessToken(user),
                tokenProvider.generateRefreshToken(user)
        );
    }

    @Override
    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
        CreateUserDto newUser = new CreateUserDto(
                registerRequestDto.username(),
                registerRequestDto.email(),
                registerRequestDto.password()
        );

        User user = userService.create(newUser);

        return new AuthResponseDto(
                tokenProvider.generateAccessToken(
                        UserPrincipal.fromEntity(user)
                ),
                tokenProvider.generateRefreshToken(
                        UserPrincipal.fromEntity(user)
                )
        );
    }

    @Override
    public AuthResponseDto refresh(RefreshRequestDto refreshRequestDto) {
        RefreshTokenClaims claims = tokenProvider.validateRefreshToken(refreshRequestDto.refreshToken())
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if(!blacklist(claims))
            throw new UnauthorizedException("Invalid refresh token");

        try {
            User user = userService.findByUsername(claims.subject());

            return new AuthResponseDto(
                    tokenProvider.generateAccessToken(
                            UserPrincipal.fromEntity(user)
                    ),
                    tokenProvider.generateRefreshToken(
                            UserPrincipal.fromEntity(user)
                    )
            );
        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

    @Override
    public void logout(RefreshRequestDto refreshRequestDto) {
        RefreshTokenClaims claims = tokenProvider.validateRefreshToken(refreshRequestDto.refreshToken())
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        blacklist(claims);
    }

    private boolean blacklist(RefreshTokenClaims claims) {
        Duration ttl = Duration.between(Instant.now(), claims.expiresAt());
        if(ttl.isNegative() || ttl.isZero()) return false;

        return cacheProvider.setIfAbsent(REFRESH_BLACKLIST_PREFIX + claims.tokenId(), true, ttl);
    }
}
