package me.andregarcia0412.platinado.modules.game.interfaces;

import me.andregarcia0412.platinado.modules.game.dtos.CreateGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.ReturnGameDto;
import me.andregarcia0412.platinado.modules.game.dtos.UpdateGameDto;
import me.andregarcia0412.platinado.shared.dto.PageResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface IGameService {
    ReturnGameDto create(CreateGameDto createGameDto);
    PageResponseDto<ReturnGameDto> findAll(Integer gameTypeId, Pageable pageable);
    ReturnGameDto findById(Integer id);
    ReturnGameDto findBySlug(String slug);
    ReturnGameDto updateById(Integer id, UpdateGameDto updateGameDto);
    void deleteById(Integer id);
    ReturnGameDto createCoverImage(Integer id, MultipartFile file);
    String getCoverImage(Integer id);
}
