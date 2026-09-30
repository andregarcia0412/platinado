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
  gameId: number;
  gameCompletionStatus: GameCompletionStatusDto;
  hoursPlayed: number | null;
  startingDate: Date | null;
  finishingDate: Date | null;
  grade: number | null;
  note: string | null;
  createdAt: Date;
}
