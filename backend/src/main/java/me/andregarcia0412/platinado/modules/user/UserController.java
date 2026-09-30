package me.andregarcia0412.platinado.modules.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.andregarcia0412.platinado.modules.user.dtos.CreateUserDto;
import me.andregarcia0412.platinado.modules.user.dtos.ReturnUserDto;
import me.andregarcia0412.platinado.modules.user.dtos.UpdateUserDto;
import me.andregarcia0412.platinado.modules.user.interfaces.IUserService;
import me.andregarcia0412.platinado.shared.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User account management")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(
            summary = "Find the authenticated user",
            description = "Returns the user identified by the access token. The password hash is never exposed."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authenticated user returned",
                    content = @Content(schema = @Schema(implementation = ReturnUserDto.class))
            )
    })
    public ResponseEntity<ReturnUserDto> findAuthUser(
            @AuthenticationPrincipal UserPrincipal principal
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(principal.id()));
    }

    @PatchMapping
    @Operation(
            summary = "Update the authenticated user",
            description = "Partially updates the user identified by the access token. Only the fields present in the payload are changed. Resending the current username or email is a no-op rather than a conflict."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authenticated user updated",
                    content = @Content(schema = @Schema(implementation = ReturnUserDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "409", description = "Username or email already taken by another user", content = @Content)
    })
    public ResponseEntity<ReturnUserDto> updateAuthUser(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid UpdateUserDto updateUserDto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateById(principal.id(), updateUserDto));
    }

    @DeleteMapping
    @Operation(
            summary = "Delete the authenticated user",
            description = "Deletes the account of the user identified by the access token, along with the games in their library. Returns no content on success."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Authenticated user deleted", content = @Content)
    })
    public ResponseEntity<Void> deleteAuthUser(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.deleteById(principal.id());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Find a user by id",
            description = "Returns the user matching the given id. The password hash is never exposed. Requires the ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(schema = @Schema(implementation = ReturnUserDto.class))
            ),
            @ApiResponse(responseCode = "403", description = "Authenticated user is not an admin", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<ReturnUserDto> findById(
            @Parameter(description = "Id of the user", example = "1") @PathVariable Integer id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update a user by id",
            description = "Partially updates a user. Only the fields present in the payload are changed. Resending the current username or email is a no-op rather than a conflict. Requires the ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated",
                    content = @Content(schema = @Schema(implementation = ReturnUserDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "403", description = "Authenticated user is not an admin", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Username or email already taken by another user", content = @Content)
    })
    public ResponseEntity<ReturnUserDto> updateById(
            @Parameter(description = "Id of the user", example = "1") @PathVariable Integer id,
            @RequestBody @Valid UpdateUserDto updateUserDto
    ) {
       return ResponseEntity.status(HttpStatus.OK).body(userService.updateById(id, updateUserDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a user by id",
            description = "Deletes the user matching the given id. Returns no content on success. Requires the ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted", content = @Content),
            @ApiResponse(responseCode = "403", description = "Authenticated user is not an admin", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Id of the user", example = "1") @PathVariable Integer id
    ) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
