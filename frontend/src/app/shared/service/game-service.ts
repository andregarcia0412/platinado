import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { ReturnGameDto } from '../../features/catalogue/model/game.dto';
import { translateHttpError } from '../error/translate-http-error';

@Injectable({ providedIn: 'root' })
export class GameService {
  private readonly http = inject(HttpClient);

  async listGames(): Promise<ReturnGameDto[]> {
    return await firstValueFrom(this.http.get<ReturnGameDto[]>('/game')).catch(translateHttpError);
  }

  async findBySlug(slug: string): Promise<ReturnGameDto> {
    return await firstValueFrom(this.http.get<ReturnGameDto>(`/game/${slug}`)).catch(
      translateHttpError,
    );
  }
}
