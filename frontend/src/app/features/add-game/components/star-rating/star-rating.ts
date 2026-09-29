import { Component, inject, input, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormControl, FormGroupDirective } from '@angular/forms';
import { map, of } from 'rxjs';

@Component({
  imports: [],
  selector: 'app-star-rating',
  templateUrl: './star-rating.html',
})
export class StarRating {
  private readonly form = inject(FormGroupDirective, { optional: true });
  private readonly submitted = toSignal(this.form?.ngSubmit.pipe(map(() => true)) ?? of(false), {
    initialValue: false,
  });
  readonly control = input.required<FormControl<number | null>>();
  readonly max = input<number>(5);
  readonly errors = input<Record<string, string>>({});

  protected readonly hovered = signal<number | null>(null);

  protected stars() {
    return Array.from({ length: this.max() }, (_, i) => i);
  }

  protected displayed() {
    return this.hovered() ?? this.control().value ?? 0;
  }

  protected fill(index: number) {
    const diff = this.displayed() - index;
    if (diff >= 1) return 100;
    if (diff >= 0.5) return 50;
    return 0;
  }

  protected formatted() {
    const value = this.control().value;
    return value === null
      ? null
      : value.toLocaleString('pt-BR', { minimumFractionDigits: 1, maximumFractionDigits: 1 });
  }

  protected select(value: number) {
    this.control().setValue(value);
    this.control().markAsTouched();
  }

  protected clear() {
    this.control().setValue(null);
    this.hovered.set(null);
  }

  protected message(): string | null {
    const control = this.control();
    if (!this.submitted() || !control.errors) return null;

    const key = Object.keys(control.errors)[0];
    return this.errors()[key] ?? null;
  }
}
