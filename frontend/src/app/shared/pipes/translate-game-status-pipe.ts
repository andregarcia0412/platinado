import { Pipe, PipeTransform } from '@angular/core';
import { GameStatusEnum } from '../../features/add-game/enum/game-status.enum';
import { translateGameStatus } from '../../features/add-game/utils/translateGameStatus';

@Pipe({
  name: 'translateGameStatus',
})
export class TranslateGameStatusPipe implements PipeTransform {
  transform(value: string): GameStatusEnum {
    return translateGameStatus(value);
  }
}
