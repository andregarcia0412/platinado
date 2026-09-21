import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./features/auth/auth').then((m) => m.Auth),
    title: 'Auth',
  },
];
