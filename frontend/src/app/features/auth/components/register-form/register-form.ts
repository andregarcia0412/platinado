import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthNavigation } from '../../utils/auth-navigation';
import { AuthInput } from '../auth-input/auth-input';

@Component({
  imports: [ReactiveFormsModule, AuthInput],
  selector: 'app-register-form',
  templateUrl: './register-form.html',
  host: { class: 'flex-1 self-stretch' },
})
export class RegisterForm {
  private readonly fb = inject(FormBuilder);
  protected readonly navigation = inject(AuthNavigation);

  protected readonly form = this.fb.nonNullable.group(
    {
      username: [
        '',
        [Validators.required, Validators.minLength(2), Validators.pattern(/^[a-zA-Z0-9_.]+$/)],
      ],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    },
    { updateOn: 'blur' },
  );

  protected readonly messages = {
    username: {
      required: 'Nome de usuário é obrigatório',
      minlength: 'Nome de usuário precisa de pelo menos 2 letras',
      pattern: 'Nome de usuário deve conter apenas letras, dígitos e símbolos',
    },
    email: { required: 'E-mail é obrigatório', email: 'E-mail inválido' },
    password: { required: 'Senha é obrigatória', minlength: 'Senha precisa de 8 caracteres' },
  };

  protected onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
  }
}
