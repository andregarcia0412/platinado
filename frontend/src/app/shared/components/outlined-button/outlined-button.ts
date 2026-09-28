import { Component, input, output } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-outlined-button',
  templateUrl: './outlined-button.html',
})
export class OutlinedButton {
  readonly icon = input<string>();
  readonly text = input.required<string>();
  readonly onClicked = output<void>();

  onButtonClick() {
    this.onClicked.emit();
  }
}
