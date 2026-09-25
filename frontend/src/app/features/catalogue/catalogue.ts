import { Component, signal } from '@angular/core';
import { GameTypePill } from './components/game-type-pill/game-type-pill';
import { GameTypeFilterEnum } from './enum/game-type-filter.enum';

@Component({
  imports: [GameTypePill],
  selector: 'app-catalogue',
  templateUrl: './catalogue.html',
})
export class Catalogue {
  protected readonly filters = Object.values(GameTypeFilterEnum);
  protected readonly selectedFilter = signal<GameTypeFilterEnum>(GameTypeFilterEnum.ALL);

  filterBy(type: GameTypeFilterEnum) {
    this.selectedFilter.set(type);
    console.log(type);
  }
}
