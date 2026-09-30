import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, Service } from '@angular/core';
import { CreateUserGameDto, ReturnUserGameDto } from '../../features/add-game/model/user-game-dto';
import { firstValueFrom } from 'rxjs';
import { translateHttpError } from '../error/translate-http-error';
import { PageResponse } from '../model/page-response.dto';

@Injectable({ providedIn: 'root' })
export class UserGameService {
  private readonly http = inject(HttpClient);

  async addToLibrary(dto: CreateUserGameDto): Promise<ReturnUserGameDto> {
    return await firstValueFrom(this.http.post<ReturnUserGameDto>('/user-game', dto)).catch(
      translateHttpError,
    );
  }

  async getLibrary(
    page = 0,
    size = 20,
    gameCompletionStatusId?: number,
  ): Promise<PageResponse<ReturnUserGameDto>> {
    let params = new HttpParams().set('page', page).set('size', size);

    if (gameCompletionStatusId)
      params = params.set('gameCompletionStatusId', gameCompletionStatusId);

    return await firstValueFrom(
      this.http.get<PageResponse<ReturnUserGameDto>>('/user-game', { params }),
    ).catch(translateHttpError);
  }
}
