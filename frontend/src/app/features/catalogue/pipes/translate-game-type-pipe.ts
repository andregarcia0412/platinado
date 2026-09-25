import { Pipe, PipeTransform } from '@angular/core';
import { GameTypeFilterEnum } from '../enum/game-type-filter.enum';
import { translateGameType } from '../utils/translateGameType';

@Pipe({
  name: 'translateGameType',
})
export class TranslateGameTypePipe implements PipeTransform {
  transform(value: string): GameTypeFilterEnum {
    return translateGameType(value);
  }
}
