import { Pipe, PipeTransform } from '@angular/core';
import { GameTypeFilterEnum } from '../../features/catalogue/enum/game-type-filter.enum';
import { translateGameType } from '../../features/catalogue/utils/translateGameType';

@Pipe({
  name: 'translateGameType',
})
export class TranslateGameTypePipe implements PipeTransform {
  transform(value: string): GameTypeFilterEnum {
    return translateGameType(value);
  }
}
