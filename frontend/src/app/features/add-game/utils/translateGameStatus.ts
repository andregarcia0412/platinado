import { GameStatusEnum } from '../enum/game-status.enum';

const TRANSLATIONS: Record<string, GameStatusEnum> = {
  Queued: GameStatusEnum.QUEUED,
  Playing: GameStatusEnum.PLAYING,
  Finished: GameStatusEnum.FINISHED,
  'Maxed Out': GameStatusEnum.MAXED_OUT,
  Abandoned: GameStatusEnum.ABANDONED,
};

export const translateGameStatus = (gameStatus: string): GameStatusEnum => {
  return TRANSLATIONS[gameStatus];
};
