import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'auth',
    loadComponent: () => import('./features/auth/auth').then((m) => m.Auth),
    title: 'Auth',
  },
  {
    path: 'my-games',
    loadComponent: () => import('./features/my-games/my-games').then((m) => m.MyGames),
    title: 'My Games',
  },
];
