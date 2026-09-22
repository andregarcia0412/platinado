package me.andregarcia0412.platinado.modules.game.interfaces;

import me.andregarcia0412.platinado.modules.game.dtos.CreateGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.ReturnGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.UpdateGameDto;

import java.util.List;

public interface IGameService {
    ReturnGameDto create(CreateGameDto createGameDto);
    List<ReturnGameDto> findAll();
    ReturnGameDto findById(Integer id);
    ReturnGameDto findBySlug(String slug);
    ReturnGameDto updateById(Integer id, UpdateGameDto updateGameDto);
    void deleteById(Integer id);
}
