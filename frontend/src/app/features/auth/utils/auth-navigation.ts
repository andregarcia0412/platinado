import { Injectable, signal } from '@angular/core';

export type AuthFormType = 'login' | 'register' | 'forgot-password';

@Injectable()
export class AuthNavigation {
  readonly current = signal<AuthFormType>('login');

  goTo(form: AuthFormType): void {
    this.current.set(form);
  }
}
