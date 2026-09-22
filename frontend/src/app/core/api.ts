import { HttpErrorResponse, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { inject, InjectionToken } from '@angular/core';
import { TokenService } from '../features/auth/services/token-service';
import { RefreshService } from '../features/auth/services/refresh-service';
import { catchError, switchMap, throwError } from 'rxjs';

export const API_BASE_URL = new InjectionToken<string>('API_BASE_URL');

export const baseUrlInterceptor: HttpInterceptorFn = (req, next) => {
  if (!req.url.startsWith('/')) return next(req);

  const baseUrl = inject(API_BASE_URL);
  return next(req.clone({ url: `${baseUrl}${req.url}` }));
};

const withToken = (req: HttpRequest<unknown>, token: string) =>
  req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const refreshService = inject(RefreshService);

  const accessToken = tokenService.accessToken;
  const authReq = accessToken ? withToken(req, accessToken) : req;

  return next(authReq).pipe(
    catchError((error) => {
      const isAuthRoute = req.url.includes('/auth');
      if (!(error instanceof HttpErrorResponse) || error.status !== 401 || isAuthRoute)
        return throwError(() => error);

      return refreshService.refresh().pipe(switchMap((newToken) => next(withToken(req, newToken))));
    }),
  );
};
