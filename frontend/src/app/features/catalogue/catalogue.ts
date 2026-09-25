import { Component } from '@angular/core';
import { GameTypePill } from './components/game-type-pill/game-type-pill';

@Component({
  imports: [GameTypePill],
  selector: 'app-catalogue',
  templateUrl: './catalogue.html',
})
export class Catalogue {
  filterBy(type: string) {
    console.log(type);
  }
}
