package me.andregarcia0412.platinado.modules.game;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.andregarcia0412.platinado.modules.game.dtos.CreateGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.ReturnGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.UpdateGameDto;
import me.andregarcia0412.platinado.modules.game.interfaces.IGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@Tag(name = "Game")
public class GameController {
    private final IGameService gameService;

    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<ReturnGameDto> create(@RequestBody @Valid CreateGameDto createGameDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.create(createGameDto));
    }

    @GetMapping
    public ResponseEntity<List<ReturnGameDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.findAll());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ReturnGameDto> findBySlug(@PathVariable String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.findBySlug(slug));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReturnGameDto> findById(
            @PathVariable Integer id,
            @RequestBody @Valid UpdateGameDto updateGameDto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.updateById(id, updateGameDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        gameService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
