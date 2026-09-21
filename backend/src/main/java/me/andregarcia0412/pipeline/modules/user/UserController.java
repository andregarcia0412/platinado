package me.andregarcia0412.pipeline.modules.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.andregarcia0412.pipeline.modules.user.dtos.CreateUserDto;
import me.andregarcia0412.pipeline.modules.user.dtos.ReturnUserDto;
import me.andregarcia0412.pipeline.modules.user.dtos.UpdateUserDto;
import me.andregarcia0412.pipeline.modules.user.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User account management")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find a user by id",
            description = "Returns the user matching the given id. The password hash is never exposed."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(schema = @Schema(implementation = ReturnUserDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<ReturnUserDto> findById(
            @Parameter(description = "Id of the user", example = "1") @PathVariable Integer id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update a user by id",
            description = "Partially updates a user. Only the fields present in the payload are changed. Resending the current username or email is a no-op rather than a conflict."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated",
                    content = @Content(schema = @Schema(implementation = ReturnUserDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
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
    @Operation(
            summary = "Delete a user by id",
            description = "Deletes the user matching the given id. Returns no content on success."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Id of the user", example = "1") @PathVariable Integer id
    ) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
