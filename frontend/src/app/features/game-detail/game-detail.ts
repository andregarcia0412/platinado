import { Component, computed, inject, input, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Header } from '../../shared/components/header/header';
import { OutlinedButton } from '../../shared/components/outlined-button/outlined-button';
import { NotFoundError } from '../../shared/error/not-found.error';
import { ParseYearPipe } from '../../shared/pipes/parse-year-pipe';
import { TranslateGameTypePipe } from '../../shared/pipes/translate-game-type-pipe';
import { GameService } from '../../shared/service/game-service';
import { ReturnGameDto } from '../catalogue/model/game.dto';
import { DetailItem } from './components/detail-item/detail-item';
import { GameDetailCard } from '../../shared/components/game-detail-card/game-detail-card';
import { GameDetailSkeleton } from './components/game-detail-skeleton/game-detail-skeleton';

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

  onAddClick() {
    this.router.navigate(['/add-game'], {
      state: {
        game: this.game(),
      },
    });
  }

  onReturnClick() {
    this.router.navigate(['/catalogue']);
  }
}
