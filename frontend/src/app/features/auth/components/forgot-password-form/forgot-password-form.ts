import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthNavigation } from '../../utils/auth-navigation';
import { hasEmptyField } from '../../utils/has-empty-field';
import { AuthButton } from '../auth-button/auth-button';
import { AuthInput } from '../auth-input/auth-input';

@Component({
  imports: [ReactiveFormsModule, AuthInput, AuthButton],
  selector: 'app-forgot-password-form',
  templateUrl: './forgot-password-form.html',
  host: { class: 'flex-1 self-stretch ' },
})
export class ForgotPasswordForm {
  private readonly fb = inject(FormBuilder);
  protected readonly navigation = inject(AuthNavigation);

  protected readonly form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
  });

  protected readonly buttonDisabled = hasEmptyField(this.form);

  protected readonly messages = {
    email: { required: 'E-mail é obrigatório', email: 'E-mail inválido' },
  };

  protected onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
  }
}
