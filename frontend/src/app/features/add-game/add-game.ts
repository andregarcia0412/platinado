import { Component, input, signal } from '@angular/core';
import { GameDetailCard } from '../../shared/components/game-detail-card/game-detail-card';
import { Header } from '../../shared/components/header/header';
import { ReturnGameDto } from '../catalogue/model/game.dto';
import { ParseYearPipe } from '../../shared/pipes/parse-year-pipe';
import { GAME_STATUS } from './utils/game-status';
import { IconPill } from '../../shared/components/icon-pill/icon-pill';
import { GameStatusEnum } from './enum/game-status.enum';

@Component({
  imports: [Header, GameDetailCard, ParseYearPipe, IconPill],
  selector: 'app-add-game',
  templateUrl: './add-game.html',
})
export class AddGame {
  protected readonly game = signal<ReturnGameDto>(history.state.game);
  protected readonly statuses = Object.values(GAME_STATUS);
  protected readonly selectedStatus = signal<GameStatusEnum | null>(null);

  selectStatus(status: GameStatusEnum) {
    this.selectedStatus.set(status);
  }
}
