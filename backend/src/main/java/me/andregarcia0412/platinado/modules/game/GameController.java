package me.andregarcia0412.platinado.modules.game;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.andregarcia0412.platinado.modules.game.dtos.CreateGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.ReturnGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.UpdateGameDto;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/game")
@Tag(name = "Game", description = "Game catalog management")
public class GameController {
    private final IGameService gameService;

    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @Operation(
            summary = "Create a game",
            description = "Creates a new game linked to an existing game type. The slug must be unique."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Game created",
                    content = @Content(schema = @Schema(implementation = ReturnGameDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "404", description = "Game type not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "A game with this slug already exists", content = @Content)
    })
    public ResponseEntity<ReturnGameDto> create(@RequestBody @Valid CreateGameDto createGameDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.create(createGameDto));
    }

    @GetMapping
    @Operation(
            summary = "List all games",
            description = "Returns every game in the catalog."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Games listed",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReturnGameDto.class)))
            )
    })
    public ResponseEntity<List<ReturnGameDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.findAll());
    }

    @GetMapping("/{slug}")
    @Operation(
            summary = "Find a game by slug",
            description = "Returns the game matching the given slug."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Game found",
                    content = @Content(schema = @Schema(implementation = ReturnGameDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)
    })
    public ResponseEntity<ReturnGameDto> findBySlug(
            @Parameter(description = "Slug of the game", example = "the-legend-of-zelda") @PathVariable String slug
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.findBySlug(slug));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update a game by id",
            description = "Partially updates a game. Only the fields present in the payload are changed. Resending the current slug is a no-op rather than a conflict."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Game updated",
                    content = @Content(schema = @Schema(implementation = ReturnGameDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "404", description = "Game or game type not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "A game with this slug already exists", content = @Content)
    })
    public ResponseEntity<ReturnGameDto> updateById(
            @Parameter(description = "Id of the game", example = "1") @PathVariable Integer id,
            @RequestBody @Valid UpdateGameDto updateGameDto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.updateById(id, updateGameDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a game by id",
            description = "Deletes the game matching the given id. Returns no content on success."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Game deleted", content = @Content)
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Id of the game", example = "1") @PathVariable Integer id
    ) {
        gameService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload a game cover image",
            description = "Uploads a cover image for the game and stores it in the bucket. Accepts JPEG, PNG or WEBP up to 5MB. Replaces and deletes the previous cover, if any."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cover image uploaded",
                    content = @Content(schema = @Schema(implementation = ReturnGameDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Missing file, empty file or unsupported image type", content = @Content),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content),
            @ApiResponse(responseCode = "413", description = "File exceeds the maximum upload size", content = @Content),
            @ApiResponse(responseCode = "500", description = "Failed to store the file", content = @Content)
    })
    public ResponseEntity<ReturnGameDto> createCoverImage(
            @Parameter(description = "Id of the game", example = "1") @PathVariable Integer id,
            @Parameter(description = "Cover image file (JPEG, PNG or WEBP, max 5MB)") @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.createCoverImage(id, file));
    }

    @GetMapping("/{id}/cover")
    @Operation(
            summary = "Get a game cover image",
            description = "Redirects to a presigned URL of the game's cover image in the bucket. The URL is valid for 15 minutes."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "307",
                    description = "Redirect to the cover image presigned URL (see the Location header)",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)
    })
    public ResponseEntity<Void> getGameCover(
            @Parameter(description = "Id of the game", example = "1") @PathVariable Integer id
    ) {
        return ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create(gameService.getCoverImage(id)))
                .build();
    }
}
