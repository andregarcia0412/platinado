package me.andregarcia0412.platinado.modules.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.andregarcia0412.platinado.modules.auth.dtos.AuthResponseDto;
import me.andregarcia0412.platinado.modules.auth.dtos.LoginRequestDto;
import me.andregarcia0412.platinado.modules.auth.dtos.RefreshRequestDto;
import me.andregarcia0412.platinado.modules.auth.dtos.RegisterRequestDto;
import me.andregarcia0412.platinado.modules.auth.interfaces.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication and session management")
@SecurityRequirements
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Log in with username and password",
            description = "Authenticates the user and returns a short-lived access token (15 minutes) and a refresh token (7 days)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authenticated",
                    content = @Content(schema = @Schema(implementation = AuthResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and returns an access token and a refresh token, so the user is logged in right away."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered",
                    content = @Content(schema = @Schema(implementation = AuthResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "409", description = "Username or email already taken", content = @Content)
    })
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequestDto));
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Refresh the session tokens",
            description = "Exchanges a valid refresh token for a new access token and a new refresh token. The refresh token is single-use: once exchanged it is revoked and reusing it returns 401."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tokens refreshed",
                    content = @Content(schema = @Schema(implementation = AuthResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Refresh token is invalid, expired or already used", content = @Content)
    })
    public ResponseEntity<AuthResponseDto> refresh(@RequestBody @Valid RefreshRequestDto refreshRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.refresh(refreshRequestDto));
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Log out",
            description = "Revokes the given refresh token so it can no longer be used to obtain new tokens. Calling it again with an already revoked token is a no-op. The current access token stays valid until it expires."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Refresh token revoked", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Refresh token is invalid or expired", content = @Content)
    })
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshRequestDto refreshRequestDto) {
        authService.logout(refreshRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
