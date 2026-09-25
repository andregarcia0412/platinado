import { Routes } from '@angular/router';
import { Auth } from './features/auth/auth';
import { authGuard } from './features/auth/guards/auth.guard';
import { MyGames } from './features/my-games/my-games';
import { Catalogue } from './features/catalogue/catalogue';

export const routes: Routes = [
  {
    path: 'auth',
    component: Auth,
    title: 'Auth',
  },
  {
    path: 'my-games',
    component: MyGames,
    title: 'My Games',
    canActivate: [authGuard],
  },
  {
    path: 'catalogue',
    component: Catalogue,
    title: 'Catalogue',
  },
];
