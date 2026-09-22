import { inject, Injectable, signal } from '@angular/core';
import { StorageService } from '../../../shared/service/storage-service';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private storageService = inject(StorageService);
  private readonly REFRESH_KEY = '@refresh_token';

  private accessSignal = signal<string | null>(null);
  private refreshSignal = signal<string | null>(this.storageService.get<string>(this.REFRESH_KEY));

  get accessToken(): string | null {
    return this.accessSignal();
  }

  get refreshToken(): string | null {
    return this.refreshSignal();
  }

  setAccessToken(token: string): void {
    this.accessSignal.set(token);
  }

  setRefreshToken(token: string): void {
    this.refreshSignal.set(token);
    this.storageService.set(this.REFRESH_KEY, token);
  }

  clearToken(): void {
    this.accessSignal.set(null);
    this.refreshSignal.set(null);
    this.storageService.remove(this.REFRESH_KEY);
  }
}
