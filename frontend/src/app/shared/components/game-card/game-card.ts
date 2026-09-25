import { Component, input } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-game-card',
  templateUrl: './game-card.html',
})
export class GameCard {
  readonly name = input.required<string>();
  readonly releaseYear = input.required<string>();
  readonly gameType = input.required<string>();
  readonly cover = input<string>();
}
