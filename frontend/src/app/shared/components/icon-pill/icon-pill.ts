import { Component, input, output } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-icon-pill',
  templateUrl: './icon-pill.html',
})
export class IconPill {
  readonly text = input.required<string>();
  readonly icon = input.required<string>();
  readonly isActive = input<boolean>(false);
  readonly onClicked = output<void>();

  onPillClick() {
    this.onClicked.emit();
  }
}
