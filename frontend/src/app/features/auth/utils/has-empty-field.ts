import { Signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormGroup } from '@angular/forms';
import { map } from 'rxjs';

export function hasEmptyField(form: FormGroup): Signal<boolean> {
  const check = () => Object.values(form.getRawValue()).some((value) => !value);

  return toSignal(form.valueChanges.pipe(map(check)), { initialValue: check() });
}
