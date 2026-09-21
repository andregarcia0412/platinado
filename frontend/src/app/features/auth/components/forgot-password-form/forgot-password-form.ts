import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthInput } from '../auth-input/auth-input';
import { AuthNavigation } from '../../utils/auth-navigation';

@Component({
  imports: [ReactiveFormsModule, AuthInput],
  selector: 'app-forgot-password-form',
  templateUrl: './forgot-password-form.html',
})
export class ForgotPasswordForm {
  private readonly fb = inject(FormBuilder);
  protected readonly navigation = inject(AuthNavigation);

  protected readonly form = this.fb.nonNullable.group(
    {
      email: ['', [Validators.required, Validators.email]],
    },
    { updateOn: 'submit' },
  );

  protected readonly messages = {
    email: { required: 'E-mail é obrigatório', email: 'E-mail inválido' },
  };

  protected onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
  }
}
