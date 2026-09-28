import { GameTypeFilterEnum } from '../enum/game-type-filter.enum';

export const GAME_TYPE_IDS: Record<GameTypeFilterEnum, number | undefined> = {
  [GameTypeFilterEnum.ALL]: undefined,
  [GameTypeFilterEnum.MAIN_GAME]: 1,
  [GameTypeFilterEnum.DLC]: 2,
  [GameTypeFilterEnum.EXPANSION]: 3,
  [GameTypeFilterEnum.BUNDLE]: 4,
  [GameTypeFilterEnum.STANDALONE_EXPANSION]: 5,
  [GameTypeFilterEnum.MOD]: 6,
  [GameTypeFilterEnum.EPISODE]: 7,
  [GameTypeFilterEnum.SEASON]: 8,
  [GameTypeFilterEnum.REMAKE]: 9,
  [GameTypeFilterEnum.REMASTER]: 10,
  [GameTypeFilterEnum.EXPANDED_GAME]: 11,
  [GameTypeFilterEnum.PORT]: 12,
  [GameTypeFilterEnum.FORK]: 13,
  [GameTypeFilterEnum.PACK]: 14,
  [GameTypeFilterEnum.UPDATE]: 15,
};
