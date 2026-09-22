package me.andregarcia0412.platinado.modules.auth.interfaces;

import me.andregarcia0412.platinado.modules.auth.dtos.AuthResponseDto;
import me.andregarcia0412.platinado.modules.auth.dtos.LoginRequestDto;
import me.andregarcia0412.platinado.modules.auth.dtos.RefreshRequestDto;
import me.andregarcia0412.platinado.modules.auth.dtos.RegisterRequestDto;

public interface IAuthService {
    AuthResponseDto login(LoginRequestDto loginRequestDto);
    AuthResponseDto register(RegisterRequestDto registerRequestDto);
    AuthResponseDto refresh(RefreshRequestDto refreshRequestDto);
    void logout(RefreshRequestDto refreshRequestDto);
}
