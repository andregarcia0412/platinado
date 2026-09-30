package me.andregarcia0412.platinado.modules.usergame.interfaces;

import me.andregarcia0412.platinado.modules.usergame.dtos.CreateUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.dtos.ReturnUserGameDto;
import me.andregarcia0412.platinado.modules.usergame.dtos.UpdateUserGameDto;
import me.andregarcia0412.platinado.shared.dto.PageResponseDto;
import org.springframework.data.domain.Pageable;


public interface IUserGameService {
    ReturnUserGameDto create(Integer userId, CreateUserGameDto createUserGameDto);
    ReturnUserGameDto findById(Integer userId, Integer gameId);
    PageResponseDto<ReturnUserGameDto> findAll(Integer userId, Integer gameCompletionStatusId, Pageable pageable);
    ReturnUserGameDto updateById(Integer userId, Integer gameId, UpdateUserGameDto updateUserGameDto);
    void deleteById(Integer userId, Integer gameId);
}
