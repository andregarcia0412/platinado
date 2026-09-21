import { Component, inject, input, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormControl, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { map, of } from 'rxjs';

let nextId = 0;

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-auth-input',
  templateUrl: './auth-input.html',
})
export class AuthInput {
  private readonly form = inject(FormGroupDirective, { optional: true });
  private readonly submitted = toSignal(this.form?.ngSubmit.pipe(map(() => true)) ?? of(false), {
    initialValue: false,
  });

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
    if (!this.submitted() || !control.errors) return null;

    const key = Object.keys(control.errors)[0];
    return this.errors()[key] ?? null;
  }

  protected toggleShowPassword(): void {
    this.showPassword.set(!this.showPassword());
  }
}
