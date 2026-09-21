import { Component, input } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-auth-button',
  templateUrl: './auth-button.html',
})
export class AuthButton {
  readonly disabled = input<boolean>(false);
  readonly title = input.required<string>();
}
