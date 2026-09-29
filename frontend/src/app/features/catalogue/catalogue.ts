import { Component, inject, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { GameCardSkeleton } from '../../shared/components/game-card-skeleton/game-card-skeleton';
import { GameCard } from '../../shared/components/game-card/game-card';
import { Header } from '../../shared/components/header/header';
import { OutlinedButton } from '../../shared/components/outlined-button/outlined-button';
import { InViewport } from '../../shared/directives/in-viewport';
import { ParseYearPipe } from '../../shared/pipes/parse-year-pipe';
import { RangePipe } from '../../shared/pipes/range-pipe';
import { TranslateGameTypePipe } from '../../shared/pipes/translate-game-type-pipe';
import { GameService } from '../../shared/service/game-service';
import { GameTypePill } from './components/game-type-pill/game-type-pill';
import { GameTypeFilterEnum } from './enum/game-type-filter.enum';
import { ReturnGameDto } from './model/game.dto';
import { GAME_TYPE_IDS } from './utils/game-type-id';

@Component({
  imports: [
    GameTypePill,
    Header,
    GameCard,
    ParseYearPipe,
    TranslateGameTypePipe,
    GameCardSkeleton,
    RangePipe,
    InViewport,
    OutlinedButton,
  ],
  selector: 'app-catalogue',
  templateUrl: './catalogue.html',
})
export class Catalogue implements OnInit {
  private readonly gameService = inject(GameService);
  protected readonly filters = Object.values(GameTypeFilterEnum);
  protected readonly selectedFilter = signal<GameTypeFilterEnum>(GameTypeFilterEnum.ALL);
  private readonly router = inject(Router);

  protected readonly games = signal<ReturnGameDto[]>([]);
  protected readonly isLoading = signal<boolean>(true);
  protected readonly isLoadingMore = signal<boolean>(false);
  protected readonly hasMore = signal<boolean>(true);
  protected readonly error = signal<string | null>(null);

  private page = 0;
  private requestId = 0;

  async ngOnInit() {
    this.reset();
  }

  filterBy(type: GameTypeFilterEnum) {
    if (type === this.selectedFilter()) return;
    this.selectedFilter.set(type);
    this.reset();
  }

  private reset() {
    this.page = 0;
    this.games.set([]);
    this.error.set(null);
    this.hasMore.set(true);
    this.isLoading.set(true);
    this.isLoadingMore.set(false);
    this.loadMore();
  }

  async loadMore() {
    if (this.isLoadingMore() || !this.hasMore()) return;
    const id = ++this.requestId;
    this.isLoadingMore.set(true);
    try {
      const res = await this.gameService.listGames(
        this.page,
        20,
        GAME_TYPE_IDS[this.selectedFilter()],
      );
      if (id !== this.requestId) return;
      this.games.update((games) => [...games, ...res.content]);
      this.hasMore.set(!res.last);
      this.page++;
    } catch (e) {
      if (id === this.requestId && e instanceof Error) this.error.set(e.message);
    } finally {
      if (id === this.requestId) {
        this.isLoading.set(false);
        this.isLoadingMore.set(false);
      }
    }
  }

  onSeeAllClicked() {
    this.filterBy(GameTypeFilterEnum.ALL);
  }
}
