import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'auth',
    loadComponent: () => import('./features/auth/auth').then((m) => m.Auth),
    title: 'Auth',
  },
  {
    path: 'home',
    loadComponent: () => import('./features/home/home').then((m) => m.Home),
    title: 'Home',
  },
];
