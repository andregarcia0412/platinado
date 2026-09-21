import { Component, input, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

let nextId = 0;

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-auth-input',
  templateUrl: './auth-input.html',
})
export class AuthInput {
  protected readonly inputId = `auth-input-${nextId++}`;
  protected readonly showPassword = signal<boolean>(false);

  readonly control = input.required<FormControl<string>>();
  readonly errors = input<Record<string, string>>({});
  readonly label = input.required<string>();
  readonly placeholder = input.required<string>();
  readonly type = input<'text' | 'email' | 'password'>('text');
  readonly autocomplete = input<string>('off');

  protected message(): string | null {
    const control = this.control();
    if (!control.touched || !control.errors) return null;

    const key = Object.keys(control.errors)[0];
    return this.errors()[key] ?? null;
  }

  protected toggleShowPassword(): void {
    this.showPassword.set(!this.showPassword());
  }
}
