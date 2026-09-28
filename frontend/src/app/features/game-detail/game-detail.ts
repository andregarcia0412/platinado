import { Component, computed, inject, input, OnInit, signal } from '@angular/core';
import { ReturnGameDto } from '../catalogue/model/game.dto';
import { GameService } from '../../shared/service/game-service';
import { Header } from '../../shared/components/header/header';
import { ParseYearPipe } from '../catalogue/pipes/parse-year-pipe';
import { OutlinedButton } from '../../shared/components/outlined-button/outlined-button';
import { TranslateGameTypePipe } from '../catalogue/pipes/translate-game-type-pipe';
import { DetailItem } from './components/detail-item/detail-item';

@Component({
  imports: [Header, ParseYearPipe, OutlinedButton, TranslateGameTypePipe, DetailItem],
  selector: 'app-game-detail',
  templateUrl: './game-detail.html',
})
export class GameDetail implements OnInit {
  private readonly gameService = inject(GameService);
  protected readonly game = signal<ReturnGameDto | null>(null);
  protected readonly isLoading = signal<boolean>(true);
  protected readonly error = signal<string | null>(null);
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
      if (e instanceof Error) this.error.set(e.message);
    } finally {
      this.isLoading.set(false);
    }
  }
}
