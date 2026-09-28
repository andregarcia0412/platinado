import { Component, inject, input, OnInit, signal } from '@angular/core';
import { ReturnGameDto } from '../catalogue/model/game.dto';
import { GameService } from '../../shared/service/game-service';

@Component({
  imports: [],
  selector: 'app-game-detail',
  templateUrl: './game-detail.html',
})
export class GameDetail implements OnInit {
  private readonly gameService = inject(GameService);
  protected readonly game = signal<ReturnGameDto | null>(null);
  protected readonly isLoading = signal<boolean>(true);
  protected readonly error = signal<string | null>(null);

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
