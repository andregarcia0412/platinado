import { HttpBackend, HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError, map, catchError, shareReplay, finalize } from 'rxjs';
import { API_BASE_URL } from '../../../core/api';
import { TokenService } from './token-service';
import { MissingTokenError } from '../error/missing-token.error';
import { AuthResponseDto } from '../model/auth-dto';

@Injectable({
  providedIn: 'root',
})
export class RefreshService {
  private http = new HttpClient(inject(HttpBackend));
  private tokenService = inject(TokenService);
  private baseUrl = inject(API_BASE_URL);
  private router = inject(Router);

  private refresh$: Observable<string> | null = null;

  refresh(): Observable<string> {
    if (this.refresh$) return this.refresh$;

    const refreshToken = this.tokenService.refreshToken;
    if (!refreshToken) {
      this.logout();
      return throwError(() => new MissingTokenError('Missing refresh token'));
    }

    this.refresh$ = this.http
      .post<AuthResponseDto>(`${this.baseUrl}/auth/refresh`, {
        refreshToken,
      })
      .pipe(
        map((res) => {
          const access = res.accessToken;
          if (!access) throw new MissingTokenError('Access token not found on response body');

          this.tokenService.setAccessToken(access);
          if (res.refreshToken) {
            this.tokenService.setRefreshToken(res.refreshToken);
          }

          return access;
        }),
        catchError((err) => {
          this.logout(refreshToken);
          return throwError(() => err);
        }),
        finalize(() => (this.refresh$ = null)),
        shareReplay(1),
      );

    return this.refresh$;
  }

  logout(refreshToken?: string | null): void {
    if (refreshToken) {
      this.http
        .post(`${this.baseUrl}/auth/logout`, {
          refreshToken,
        })
        .subscribe({ error: () => {} });
    }

    this.tokenService.clearToken();
    this.router.navigate(['/auth']);
  }
}
