import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { ReturnGameDto } from '../model/game.dto';
import { firstValueFrom } from 'rxjs';
import { translateHttpError } from '../../../shared/error/translate-http-error';

@Service()
export class GameService {
  private readonly http = inject(HttpClient);

  async listGames(): Promise<ReturnGameDto[]> {
    return await firstValueFrom(this.http.get<ReturnGameDto[]>('/game')).catch(translateHttpError);
  }
}
