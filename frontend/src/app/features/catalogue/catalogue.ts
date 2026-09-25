import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { GameCard } from '../../shared/components/game-card/game-card';
import { Header } from '../../shared/components/header/header';
import { GameTypePill } from './components/game-type-pill/game-type-pill';
import { GameTypeFilterEnum } from './enum/game-type-filter.enum';
import { ReturnGameDto } from './model/game.dto';
import { ParseYearPipe } from './pipes/parse-year-pipe';
import { GameService } from './services/game-service';
import { translateGameType } from './utils/translateGameType';
import { TranslateGameTypePipe } from './pipes/translate-game-type-pipe';
import { GameCardSkeleton } from '../../shared/components/game-card-skeleton/game-card-skeleton';

@Component({
  imports: [GameTypePill, Header, GameCard, ParseYearPipe, TranslateGameTypePipe, GameCardSkeleton],
  selector: 'app-catalogue',
  templateUrl: './catalogue.html',
})
export class Catalogue implements OnInit {
  private readonly gameService = inject(GameService);
  protected readonly filters = Object.values(GameTypeFilterEnum);
  protected readonly selectedFilter = signal<GameTypeFilterEnum>(GameTypeFilterEnum.ALL);

  protected readonly games = signal<ReturnGameDto[]>([]);
  protected readonly filteredGames = computed(() => {
    const filter = this.selectedFilter();
    if (filter === GameTypeFilterEnum.ALL) return this.games();
    return this.games().filter((game) => translateGameType(game.gameType.type) === filter);
  });
  protected readonly isLoading = signal<boolean>(true);
  protected readonly error = signal<string | null>(null);

  async ngOnInit() {
    try {
      this.games.set(await this.gameService.listGames());
      this.isLoading.set(false);
    } catch (e) {
      if (e instanceof Error) this.error.set(e.message);
    }
  }

  filterBy(type: GameTypeFilterEnum) {
    this.selectedFilter.set(type);
  }
}
