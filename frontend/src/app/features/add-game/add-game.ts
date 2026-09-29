import { Component, computed, inject, signal } from '@angular/core';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators,
  ɵInternalFormsSharedModule,
} from '@angular/forms';
import { GameDetailCard } from '../../shared/components/game-detail-card/game-detail-card';
import { Header } from '../../shared/components/header/header';
import { IconPill } from '../../shared/components/icon-pill/icon-pill';
import { OutlinedButton } from '../../shared/components/outlined-button/outlined-button';
import { ReturnToScreen } from '../../shared/components/return-to-screen/return-to-screen';
import { ParseYearPipe } from '../../shared/pipes/parse-year-pipe';
import { ReturnGameDto } from '../catalogue/model/game.dto';
import { AddGameInput } from './components/add-game-input/add-game-input';
import { StarRating } from './components/star-rating/star-rating';
import { GameStatusEnum } from './enum/game-status.enum';
import { GAME_STATUS } from './utils/game-status';

@Component({
  imports: [
    Header,
    GameDetailCard,
    ParseYearPipe,
    IconPill,
    ReturnToScreen,
    AddGameInput,
    StarRating,
    ɵInternalFormsSharedModule,
    ReactiveFormsModule,
    OutlinedButton,
  ],
  selector: 'app-add-game',
  templateUrl: './add-game.html',
})
export class AddGame {
  private readonly fb = inject(FormBuilder);

  protected readonly form = this.fb.group({
    status: this.fb.control<GameStatusEnum | null>(null, [Validators.required]),
    rating: this.fb.control<number | null>(null, [
      Validators.required,
      Validators.min(0.5),
      Validators.max(5),
    ]),
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
    rating: {
      required: 'Dê uma nota ao jogo',
    },
  };

  protected readonly game = signal<ReturnGameDto>(history.state.game);
  protected readonly statuses = Object.values(GAME_STATUS);

  protected selectStatus(status: GameStatusEnum) {
    this.form.controls.status.setValue(status);
    if (status === GameStatusEnum.QUEUED) {
      this.resetTimeRelatedFields();
    }
  }

  protected onSubmit() {
    this.form.markAllAsTouched();
    if (this.form.invalid) {
      return;
    }
  }

  private resetTimeRelatedFields() {
    this.form.controls.hoursPlayed.reset();
    this.form.controls.startedAt.reset();
    this.form.controls.finishedAt.reset();
  }
}
