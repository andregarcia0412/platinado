import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { ReturnGameDto } from '../../features/catalogue/model/game.dto';
import { translateHttpError } from '../error/translate-http-error';
import { PageResponse } from '../model/page-response.dto';

@Injectable({ providedIn: 'root' })
export class GameService {
  private readonly http = inject(HttpClient);

  async listGames(page = 0, size = 20, gameTypeId?: number): Promise<PageResponse<ReturnGameDto>> {
    let params = new HttpParams().set('page', page).set('size', size);

    if (gameTypeId) params = params.set('gameTypeId', gameTypeId);

    return await firstValueFrom(
      this.http.get<PageResponse<ReturnGameDto>>('/game', { params }),
    ).catch(translateHttpError);
  }

  async findBySlug(slug: string): Promise<ReturnGameDto> {
    return await firstValueFrom(this.http.get<ReturnGameDto>(`/game/${slug}`)).catch(
      translateHttpError,
    );
  }
}
