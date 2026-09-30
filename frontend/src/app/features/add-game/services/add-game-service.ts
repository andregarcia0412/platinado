import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { CreateUserGameDto, ReturnUserGameDto } from '../model/user-game-dto';
import { firstValueFrom } from 'rxjs';
import { translateHttpError } from '../../../shared/error/translate-http-error';
import { PageResponse } from '../../../shared/model/page-response.dto';

@Service()
export class AddGameService {
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
