package me.andregarcia0412.pipeline.modules.user.interfaces;

import me.andregarcia0412.pipeline.modules.user.dto.CreateUserDto;
import me.andregarcia0412.pipeline.modules.user.dto.ReturnUserDto;
import me.andregarcia0412.pipeline.modules.user.dto.UpdateUserDto;
import me.andregarcia0412.pipeline.modules.user.entities.User;

public interface IUserService {
    ReturnUserDto create(CreateUserDto createUserDto);
    ReturnUserDto findById(Integer id);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    ReturnUserDto updateById(Integer id, UpdateUserDto updateUserDto);
    ReturnUserDto deleteById(Integer id);
}
