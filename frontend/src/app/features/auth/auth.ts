import { NgComponentOutlet } from '@angular/common';
import { Component, computed, inject } from '@angular/core';
import { AuthNavigation } from './utils/auth-navigation';
import { AuthFormFactory } from './utils/form.factory';

@Component({
  imports: [NgComponentOutlet],
  providers: [AuthNavigation],
  selector: 'app-auth',
  templateUrl: './auth.html',
})
export class Auth {
  private readonly navigation = inject(AuthNavigation);
  private readonly authFormFactory = inject(AuthFormFactory);

  protected readonly form = computed(() =>
    this.authFormFactory.makeAuthForm(this.navigation.current()),
  );
}
