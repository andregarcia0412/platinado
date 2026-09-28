import { Component, input } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-game-detail',
  templateUrl: './game-detail.html',
})
export class GameDetail {
  slug = input.required<string>();
}
