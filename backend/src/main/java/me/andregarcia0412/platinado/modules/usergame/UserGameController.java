package me.andregarcia0412.platinado.modules.usergame;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.andregarcia0412.platinado.modules.usergame.dtos.CreateUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.dtos.ReturnUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.dtos.UpdateUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.interfaces.IUserGameService;
import me.andregarcia0412.platinado.shared.dto.PageResponseDto;
import me.andregarcia0412.platinado.shared.security.UserPrincipal;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-game")
@Tag(name = "User Game", description = "Games in the authenticated user's library")
public class UserGameController {
    private final IUserGameService userGameService;

    public UserGameController(IUserGameService userGameService) {
        this.userGameService = userGameService;
    }

    @PostMapping
    @Operation(
            summary = "Add a game to the library",
            description = "Adds an existing game to the authenticated user's library with a completion status and optional play data. A game can only be added once per user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Game added to the library",
                    content = @Content(schema = @Schema(implementation = ReturnUserGameDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "404", description = "User, game or game completion status not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Game already in the user's library", content = @Content)
    })
    public ResponseEntity<ReturnUserGameDto> create(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid CreateUserGameDto createUserGameDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userGameService.create(principal.id(), createUserGameDto));
    }

    @GetMapping
    @Operation(
            summary = "List the library (paginated)",
            description = "Returns a page of games from the authenticated user's library, optionally filtered by completion status. Pages are zero-based. Defaults to page 0, 20 games per page, sorted by most recently added. The page size is capped at 50. An unknown completion status id returns an empty page."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of user games returned")
    })
    public ResponseEntity<PageResponseDto<ReturnUserGameDto>> findAll(
            @AuthenticationPrincipal UserPrincipal principal,
            @ParameterObject @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @Parameter(description = "Id of the game completion status to filter by. Omit to list games of every status.", example = "2")
            @RequestParam(required = false) Integer gameCompletionStatusId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userGameService.findAll(
                principal.id(),
                gameCompletionStatusId,
                pageable
        ));
    }

    @GetMapping("/{gameId}")
    @Operation(
            summary = "Find a game in the library",
            description = "Returns the entry of the given game in the authenticated user's library."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User game found",
                    content = @Content(schema = @Schema(implementation = ReturnUserGameDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Game not in the user's library", content = @Content)
    })
    public ResponseEntity<ReturnUserGameDto> findById(
            @AuthenticationPrincipal UserPrincipal principal,
            @Parameter(description = "Id of the game", example = "1") @PathVariable Integer gameId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userGameService.findById(principal.id(), gameId));
    }

    @PatchMapping("/{gameId}")
    @Operation(
            summary = "Update a game in the library",
            description = "Partially updates the entry of the given game in the authenticated user's library. Only the fields present in the payload are changed."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User game updated",
                    content = @Content(schema = @Schema(implementation = ReturnUserGameDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "404", description = "Game not in the user's library or game completion status not found", content = @Content)
    })
    public ResponseEntity<ReturnUserGameDto> updateById(
            @AuthenticationPrincipal UserPrincipal principal,
            @Parameter(description = "Id of the game", example = "1") @PathVariable Integer gameId,
            @RequestBody @Valid UpdateUserGameDto updateUserGameDto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userGameService.updateById(principal.id(), gameId, updateUserGameDto));
    }

    @DeleteMapping("/{gameId}")
    @Operation(
            summary = "Remove a game from the library",
            description = "Removes the given game from the authenticated user's library. Returns no content on success."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Game removed from the library", content = @Content)
    })
    public ResponseEntity<Void> deleteById(
            @AuthenticationPrincipal UserPrincipal principal,
            @Parameter(description = "Id of the game", example = "1") @PathVariable Integer gameId
    ) {
        userGameService.deleteById(principal.id(), gameId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
