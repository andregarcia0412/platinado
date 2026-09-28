import { Component, computed, inject, input, OnInit, signal } from '@angular/core';
import { Header } from '../../shared/components/header/header';
import { OutlinedButton } from '../../shared/components/outlined-button/outlined-button';
import { GameService } from '../../shared/service/game-service';
import { ReturnGameDto } from '../catalogue/model/game.dto';
import { ParseYearPipe } from '../catalogue/pipes/parse-year-pipe';
import { TranslateGameTypePipe } from '../catalogue/pipes/translate-game-type-pipe';
import { DetailItem } from './components/detail-item/detail-item';
import { GameDetailCard } from './components/game-detail-card/game-detail-card';
import { GameDetailSkeleton } from './components/game-detail-skeleton/game-detail-skeleton';
import { NotFoundError } from '../../shared/error/not-found.error';
import { Router } from '@angular/router';

@Component({
  imports: [
    Header,
    ParseYearPipe,
    OutlinedButton,
    TranslateGameTypePipe,
    DetailItem,
    GameDetailCard,
    GameDetailSkeleton,
  ],
  selector: 'app-game-detail',
  templateUrl: './game-detail.html',
})
export class GameDetail implements OnInit {
  private readonly gameService = inject(GameService);
  private readonly router = inject(Router);
  protected readonly game = signal<ReturnGameDto | null>(null);
  protected readonly isLoading = signal<boolean>(true);
  protected readonly error = signal<string | null>(null);
  protected readonly isNotFound = signal<boolean>(false);
  protected readonly releaseDate = computed(() => {
    const date = this.game()?.firstReleaseDate;
    return date ? new Date(date) : null;
  });
  protected readonly createdAt = computed(() => {
    const date = this.game()?.createdAt;
    return date ? new Date(date) : null;
  });

  slug = input.required<string>();

  async ngOnInit(): Promise<void> {
    try {
      this.game.set(await this.gameService.findBySlug(this.slug()));
      console.log(this.game());
    } catch (e) {
      if (e instanceof NotFoundError) {
        this.isNotFound.set(true);
        return;
      }
      if (e instanceof Error) this.error.set(e.message);
    } finally {
      this.isLoading.set(false);
    }
  }

  onReturnClick() {
    this.router.navigate(['/catalogue']);
  }
}
