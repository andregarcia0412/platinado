import { Component, input, output } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  imports: [RouterLink],
  selector: 'app-game-card',
  templateUrl: './game-card.html',
})
export class GameCard {
  readonly name = input.required<string>();
  readonly releaseYear = input<string>();
  readonly gameType = input.required<string>();
  readonly cover = input<string>();
  readonly slug = input.required<string>();
}
