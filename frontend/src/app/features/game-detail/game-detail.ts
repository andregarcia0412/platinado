import { Component, computed, inject, input, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { GameDetailCard } from '../../shared/components/game-detail-card/game-detail-card';
import { Header } from '../../shared/components/header/header';
import { OutlinedButton } from '../../shared/components/outlined-button/outlined-button';
import { NotFoundError } from '../../shared/error/not-found.error';
import { DateToStringPipe } from '../../shared/pipes/date-to-string-pipe';
import { FormatHoursPipe } from '../../shared/pipes/format-hours-pipe';
import { ParseYearPipe } from '../../shared/pipes/parse-year-pipe';
import { TranslateGameTypePipe } from '../../shared/pipes/translate-game-type-pipe';
import { GameService } from '../../shared/service/game-service';
import { UserGameService } from '../../shared/service/user-game-service';
import { ReturnUserGameDto } from '../add-game/model/user-game-dto';
import { ReturnGameDto } from '../catalogue/model/game.dto';
import { DetailItem } from './components/detail-item/detail-item';
import { GameDetailSkeleton } from './components/game-detail-skeleton/game-detail-skeleton';
import { IconPill } from '../../shared/components/icon-pill/icon-pill';
import { GAME_STATUS } from '../add-game/utils/game-status';
import { translateGameStatus } from '../add-game/utils/translateGameStatus';

@Component({
  imports: [
    Header,
    ParseYearPipe,
    OutlinedButton,
    TranslateGameTypePipe,
    DetailItem,
    GameDetailCard,
    GameDetailSkeleton,
    DateToStringPipe,
    FormatHoursPipe,
    IconPill,
  ],
  selector: 'app-game-detail',
  templateUrl: './game-detail.html',
})
export class GameDetail implements OnInit {
  private readonly gameService = inject(GameService);
  private readonly userGameService = inject(UserGameService);
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
  protected readonly userGame = signal<ReturnUserGameDto | null>(null);
  protected readonly startingDate = computed(() =>
    this.stringToDate(this.userGame()?.startingDate),
  );
  protected readonly finishingDate = computed(() =>
    this.stringToDate(this.userGame()?.finishingDate),
  );
  protected readonly userGameCreatedAt = computed(() =>
    this.stringToDate(this.userGame()?.createdAt),
  );
  protected readonly completionStatus = computed(() => {
    const userGame = this.userGame();
    if (!userGame) return null;
    return GAME_STATUS[translateGameStatus(userGame.gameCompletionStatus.status)];
  });

  readonly slug = input.required<string>();

  async ngOnInit(): Promise<void> {
    try {
      const game = await this.gameService.findBySlug(this.slug());
      this.userGame.set(await this.findUserGame(game.id));
      this.game.set(game);
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

  onEditClick() {}

  onReturnClick() {
    this.router.navigate(['/catalogue']);
  }

  private stringToDate(date?: string | null): Date | null {
    return date ? new Date(date) : null;
  }

  private async findUserGame(gameId: number): Promise<ReturnUserGameDto | null> {
    try {
      return await this.userGameService.getByGameId(gameId);
    } catch (e) {
      if (e instanceof NotFoundError) return null;
      throw e;
    }
  }
}
