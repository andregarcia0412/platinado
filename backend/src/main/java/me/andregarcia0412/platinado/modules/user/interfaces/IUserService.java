package me.andregarcia0412.platinado.modules.user.interfaces;

import me.andregarcia0412.platinado.modules.user.dtos.CreateUserDto;
import me.andregarcia0412.platinado.modules.user.dtos.ReturnUserDto;
import me.andregarcia0412.platinado.modules.user.dtos.UpdateUserDto;
import me.andregarcia0412.platinado.modules.user.entities.User;

public interface IUserService {
    User create(CreateUserDto createUserDto);
    ReturnUserDto findById(Integer id);
    User findByEmail(String email);
    User findByUsername(String username);
    ReturnUserDto updateById(Integer id, UpdateUserDto updateUserDto);
    void deleteById(Integer id);
}
