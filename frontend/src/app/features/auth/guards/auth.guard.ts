import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { catchError, map, of } from 'rxjs';
import { RefreshService } from '../services/refresh-service';
import { TokenService } from '../services/token-service';

export const authGuard: CanActivateFn = () => {
  const tokenService = inject(TokenService);
  const refreshService = inject(RefreshService);
  const router = inject(Router);

  if (tokenService.accessToken) return true;
  if (!tokenService.refreshToken) return router.parseUrl('/auth');

  return refreshService.refresh().pipe(
    map(() => true),
    catchError(() => of(router.parseUrl('/auth'))),
  );
};
