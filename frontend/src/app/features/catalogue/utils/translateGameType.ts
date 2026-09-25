import { GameTypeFilterEnum } from '../enum/game-type-filter.enum';

const TRANSLATIONS: Record<string, GameTypeFilterEnum> = {
  'Main Game': GameTypeFilterEnum.MAIN_GAME,
  DLC: GameTypeFilterEnum.DLC,
  Expansion: GameTypeFilterEnum.EXPANSION,
  Bundle: GameTypeFilterEnum.BUNDLE,
  'Standalone Expansion': GameTypeFilterEnum.STANDALONE_EXPANSION,
  Mod: GameTypeFilterEnum.MOD,
  Episode: GameTypeFilterEnum.EPISODE,
  Season: GameTypeFilterEnum.SEASON,
  Remake: GameTypeFilterEnum.REMAKE,
  Remaster: GameTypeFilterEnum.REMASTER,
  'Expanded Game': GameTypeFilterEnum.EXPANDED_GAME,
  Port: GameTypeFilterEnum.PORT,
  Fork: GameTypeFilterEnum.FORK,
  Pack: GameTypeFilterEnum.PACK,
  Update: GameTypeFilterEnum.UPDATE,
};

export const translateGameType = (gameType: string): GameTypeFilterEnum => {
  return TRANSLATIONS[gameType];
};
