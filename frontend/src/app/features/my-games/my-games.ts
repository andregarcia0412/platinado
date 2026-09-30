import { Component, inject, OnInit, signal } from '@angular/core';
import { Header } from '../../shared/components/header/header';
import { OutlinedButton } from '../../shared/components/outlined-button/outlined-button';
import { UserGameService } from '../../shared/service/user-game-service';
import { ReturnUserGameDto } from '../add-game/model/user-game-dto';
import { GameCard } from '../../shared/components/game-card/game-card';
import { GameCardSkeleton } from '../../shared/components/game-card-skeleton/game-card-skeleton';
import { RangePipe } from '../../shared/pipes/range-pipe';
import { ParseYearPipe } from '../../shared/pipes/parse-year-pipe';

@Component({
  imports: [OutlinedButton, Header, GameCard, GameCardSkeleton, RangePipe, ParseYearPipe],
  selector: 'app-my-games',
  templateUrl: './my-games.html',
})
export class MyGames implements OnInit {
  private readonly userGameService = inject(UserGameService);
  protected readonly games = signal<ReturnUserGameDto[]>([]);
  protected readonly isLoading = signal<boolean>(true);
  protected readonly isLoadingMore = signal<boolean>(false);
  protected readonly hasMore = signal<boolean>(true);
  protected readonly error = signal<string | null>(null);

  private page = 0;
  private requestId = 0;

  ngOnInit(): void {
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
      const res = await this.userGameService.getLibrary(this.page, 20);
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
}
