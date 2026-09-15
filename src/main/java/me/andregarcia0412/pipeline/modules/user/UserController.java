package me.andregarcia0412.pipeline.modules.user;

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
@Tag(name = "User")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ReturnUserDto> create(@RequestBody @Valid CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(createUserDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnUserDto> findById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReturnUserDto> updateById(
            @PathVariable Integer id,
            @RequestBody @Valid UpdateUserDto updateUserDto
    ) {
       return ResponseEntity.status(HttpStatus.OK).body(userService.updateById(id, updateUserDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReturnUserDto> deleteById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteById(id));
    }
}
