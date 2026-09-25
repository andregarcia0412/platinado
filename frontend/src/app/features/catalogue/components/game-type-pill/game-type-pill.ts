import { Component, input, output } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  imports: [NgClass],
  selector: 'app-game-type-pill',
  templateUrl: './game-type-pill.html',
})
export class GameTypePill {
  readonly text = input.required<string>();
  readonly selected = input<boolean>(false);
  readonly pillClick = output<void>();
}
