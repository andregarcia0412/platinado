import { Component, inject, input, signal } from '@angular/core';
import { GameDetailCard } from '../../shared/components/game-detail-card/game-detail-card';
import { Header } from '../../shared/components/header/header';
import { ReturnGameDto } from '../catalogue/model/game.dto';
import { ParseYearPipe } from '../../shared/pipes/parse-year-pipe';
import { GAME_STATUS } from './utils/game-status';
import { IconPill } from '../../shared/components/icon-pill/icon-pill';
import { GameStatusEnum } from './enum/game-status.enum';
import { ReturnToScreen } from '../../shared/components/return-to-screen/return-to-screen';
import { AddGameInput } from './components/add-game-input/add-game-input';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators,
  ɵInternalFormsSharedModule,
} from '@angular/forms';

@Component({
  imports: [
    Header,
    GameDetailCard,
    ParseYearPipe,
    IconPill,
    ReturnToScreen,
    AddGameInput,
    ɵInternalFormsSharedModule,
    ReactiveFormsModule,
  ],
  selector: 'app-add-game',
  templateUrl: './add-game.html',
})
export class AddGame {
  private readonly fb = inject(FormBuilder);

  protected readonly form = this.fb.group({
    status: this.fb.control<GameStatusEnum | null>(null, [Validators.required]),
    hoursPlayed: this.fb.control<number | null>(null, [Validators.min(0)]),
    startedAt: this.fb.control<Date | null>(null),
    finishedAt: this.fb.control<Date | null>(null),
    annotations: ['', [Validators.maxLength(512)]],
  });

  protected readonly messages = {
    status: {
      required: 'Selecione um status',
    },
    hoursPlayed: {
      min: 'Você deve ter jogado pelo menos 0 horas',
    },
    annotations: {
      maxlength: 'As anotações devem ter no máximo 512 caracteres',
    },
  };

  protected readonly game = signal<ReturnGameDto>(history.state.game);
  protected readonly statuses = Object.values(GAME_STATUS);

  protected selectStatus(status: GameStatusEnum) {
    this.form.controls.status.setValue(status);
  }

  protected onSubmit() {
    this.form.markAllAsTouched();
    if (this.form.invalid) {
      return;
    }
  }
}
