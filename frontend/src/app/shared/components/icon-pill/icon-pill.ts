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
  readonly clickable = input<boolean>(true);
  readonly onClicked = output<void>();

  onPillClick() {
    if (this.clickable()) this.onClicked.emit();
  }
}
