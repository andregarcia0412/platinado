import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { translateHttpError } from '../../../shared/error/translate-http-error';
import {
  AuthResponseDto,
  LoginRequestDto,
  RefreshRequestDto,
  RegisterRequestDto,
} from '../model/auth-dto';

@Service()
export class AuthService {
  private readonly http = inject(HttpClient);

  async register(dto: RegisterRequestDto): Promise<AuthResponseDto> {
    return await firstValueFrom(this.http.post<AuthResponseDto>('/auth/register', dto)).catch(
      translateHttpError,
    );
  }

  async login(dto: LoginRequestDto): Promise<AuthResponseDto> {
    return await firstValueFrom(this.http.post<AuthResponseDto>('/auth/login', dto)).catch(
      translateHttpError,
    );
  }

  async refresh(dto: RefreshRequestDto): Promise<AuthResponseDto> {
    return await firstValueFrom(this.http.post<AuthResponseDto>('auth/refresh', dto)).catch(
      translateHttpError,
    );
  }

  async logout(dto: RefreshRequestDto): Promise<void> {
    return await firstValueFrom(this.http.post<void>('auth/logout', dto)).catch(translateHttpError);
  }
}
