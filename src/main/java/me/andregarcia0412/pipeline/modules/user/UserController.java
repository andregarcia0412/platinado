package me.andregarcia0412.pipeline.modules.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.andregarcia0412.pipeline.modules.user.dto.CreateUserDto;
import me.andregarcia0412.pipeline.modules.user.dto.ReturnUserDto;
import me.andregarcia0412.pipeline.modules.user.dto.UpdateUserDto;
import me.andregarcia0412.pipeline.modules.user.services.IUserService;
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

    @PostMapping
    @Operation(
            summary = "Create a user",
            description = "Registers a new user. The email must not already be in use and the password is stored hashed."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created",
                    content = @Content(schema = @Schema(implementation = ReturnUserDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email already in use", content = @Content)
    })
    public ResponseEntity<ReturnUserDto> create(@RequestBody @Valid CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(createUserDto));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find a user by id",
            description = "Returns the user matching the given id. The password is never exposed."
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
            description = "Partially updates a user. Only the fields present in the payload are changed."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated",
                    content = @Content(schema = @Schema(implementation = ReturnUserDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email already in use", content = @Content)
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
            description = "Deletes the user matching the given id and returns the deleted record."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User deleted",
                    content = @Content(schema = @Schema(implementation = ReturnUserDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<ReturnUserDto> deleteById(
            @Parameter(description = "Id of the user", example = "1") @PathVariable Integer id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteById(id));
    }
}
