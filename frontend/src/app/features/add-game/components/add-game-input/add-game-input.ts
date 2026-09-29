import { Component, computed, inject, input } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormControl, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { map, of } from 'rxjs';

let nextId = 0;

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-add-game-input',
  templateUrl: './add-game-input.html',
})
export class AddGameInput {
  private readonly form = inject(FormGroupDirective, { optional: true });
  private readonly submitted = toSignal(this.form?.ngSubmit.pipe(map(() => true)) ?? of(false), {
    initialValue: false,
  });

  protected readonly inputId = `auth-input-${nextId++}`;

  readonly placeholder = input.required<string>();
  readonly type = input<'text' | 'date' | 'textarea' | 'number'>('text');
  readonly min = input<number>(0);
  readonly control = input.required<FormControl<string | number | Date | null>>();
  readonly errors = input<Record<string, string>>({});
  readonly label = input.required<string>();

  protected length() {
    return String(this.control().value ?? '').length;
  }

  protected message(): string | null {
    const control = this.control();
    if (!this.submitted() || !control.errors) return null;

    const key = Object.keys(control.errors)[0];
    return this.errors()[key] ?? null;
  }
}
