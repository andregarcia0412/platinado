import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { API_BASE_URL, baseUrlInterceptor } from './core/api';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),

    { provide: API_BASE_URL, useValue: 'http://localhost:8080' },

    provideHttpClient(withInterceptors([baseUrlInterceptor])),

    provideRouter(routes),
  ],
};
