import { ReturnGameDto } from '../../catalogue/model/game.dto';
import { GameCompletionStatusDto } from './game-completion-status-dto';

export interface CreateUserGameDto {
  gameId: number;
  gameCompletionStatusId: number;
  hoursPlayed: number | null;
  startingDate: Date | null;
  finishingDate: Date | null;
  grade: number;
  note: string | null;
}

export interface ReturnUserGameDto {
  userId: number;
  game: ReturnGameDto;
  gameCompletionStatus: GameCompletionStatusDto;
  hoursPlayed: number | null;
  startingDate: string | null;
  finishingDate: string | null;
  grade: number | null;
  note: string | null;
  createdAt: string;
}
