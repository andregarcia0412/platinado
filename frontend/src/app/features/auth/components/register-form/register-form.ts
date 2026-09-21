import { Component, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../services/auth-service';
import { AuthNavigation } from '../../utils/auth-navigation';
import { hasEmptyField } from '../../utils/has-empty-field';
import { AuthButton } from '../auth-button/auth-button';
import { AuthInput } from '../auth-input/auth-input';

@Component({
  imports: [ReactiveFormsModule, AuthInput, AuthButton],
  selector: 'app-register-form',
  templateUrl: './register-form.html',
  host: { class: 'flex-1 self-stretch' },
})
export class RegisterForm {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  protected readonly navigation = inject(AuthNavigation);

  protected readonly form = this.fb.nonNullable.group({
    username: [
      '',
      [Validators.required, Validators.minLength(2), Validators.pattern(/^[a-zA-Z0-9_.]+$/)],
    ],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    confirmPassword: ['', [Validators.required, this.passwordMatch]],
  });

  protected readonly buttonDisabled = hasEmptyField(this.form);

  protected readonly messages = {
    username: {
      required: 'Nome de usuário é obrigatório',
      minlength: 'Nome de usuário precisa de pelo menos 2 letras',
      pattern: 'Nome de usuário deve conter apenas letras, dígitos e símbolos',
    },
    email: { required: 'E-mail é obrigatório', email: 'E-mail inválido' },
    password: { required: 'Senha é obrigatória', minlength: 'Senha precisa de 8 caracteres' },
    confirmPassword: { required: 'Confirme sua senha', mismatch: 'As senhas não coincidem' },
  };

  protected readonly errorMessage = signal<string | null>(null);

  constructor() {
    this.form.controls.password.valueChanges
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.form.controls.confirmPassword.updateValueAndValidity());
  }

  protected async onSubmit(): Promise<void> {
    if (this.form.invalid) {
      return;
    }

    try {
      const form = this.form.getRawValue();
      const auth = await this.authService.register({
        username: form.username,
        email: form.email,
        password: form.password,
      });
      console.log(auth);
    } catch (e) {
      if (e instanceof Error) this.errorMessage.set(e.message);
    }
  }

  private passwordMatch(control: AbstractControl): ValidationErrors | null {
    const password = control.parent?.get('password')?.value;
    return control.value === password ? null : { mismatch: true };
  }
}
