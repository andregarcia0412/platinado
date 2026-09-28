import { Component, input } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-game-detail-card',
  templateUrl: './game-detail-card.html',
})
export class GameDetailCard {
  readonly name = input<string>();
  readonly cover = input<string>();
}
