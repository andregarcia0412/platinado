import { Component, input, output } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-game-card',
  templateUrl: './game-card.html',
})
export class GameCard {
  readonly name = input.required<string>();
  readonly releaseYear = input<string>();
  readonly gameType = input.required<string>();
  readonly cover = input<string>();
  readonly slug = input.required<string>();
  readonly gameClicked = output<string>();

  onGameClick() {
    this.gameClicked.emit(this.slug());
  }
}
