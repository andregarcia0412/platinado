import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { TokenService } from '../../services/token-service';
import { AuthNavigation } from '../../utils/auth-navigation';
import { hasEmptyField } from '../../utils/has-empty-field';
import { AuthButton } from '../auth-button/auth-button';
import { AuthInput } from '../auth-input/auth-input';

@Component({
  imports: [ReactiveFormsModule, AuthInput, AuthButton],
  selector: 'app-login-form',
  templateUrl: './login-form.html',
  host: { class: 'flex-1 self-stretch' },
})
export class LoginForm {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly tokenService = inject(TokenService);
  private readonly router = inject(Router);

  protected readonly navigation = inject(AuthNavigation);

  protected readonly form = this.fb.nonNullable.group({
    username: [
      '',
      [Validators.required, Validators.minLength(2), Validators.pattern(/^[a-zA-Z0-9_.]+$/)],
    ],
    password: ['', [Validators.required, Validators.minLength(8)]],
  });

  protected readonly buttonDisabled = hasEmptyField(this.form);
  protected readonly buttonLoading = signal<boolean>(false);

  protected readonly messages = {
    username: {
      required: 'Nome de usuário é obrigatório',
      minlength: 'Nome de usuário precisa de pelo menos 2 letras',
      pattern: 'Nome de usuário deve conter apenas letras, dígitos e símbolos',
    },
    password: { required: 'Senha é obrigatória', minlength: 'Senha precisa de 8 caracteres' },
  };

  protected readonly errorMessage = signal<string | null>(null);

  protected async onSubmit(): Promise<void> {
    if (this.form.invalid) {
      return;
    }

    try {
      this.buttonLoading.set(true);
      const { accessToken, refreshToken } = await this.authService.login(this.form.getRawValue());
      this.tokenService.setAccessToken(accessToken);
      this.tokenService.setRefreshToken(refreshToken);
      this.router.navigate(['/home']);
    } catch (e) {
      if (e instanceof Error) this.errorMessage.set(e.message);
    } finally {
      this.buttonLoading.set(false);
    }
  }
}
