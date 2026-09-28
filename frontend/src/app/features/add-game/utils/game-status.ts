import { GameStatusEnum } from '../enum/game-status.enum';

interface GameTypePillProps {
  id: number;
  value: GameStatusEnum;
  icon: string;
  label: string;
}

export const GAME_STATUS: Record<GameStatusEnum, GameTypePillProps> = {
  [GameStatusEnum.QUEUED]: {
    id: 1,
    value: GameStatusEnum.QUEUED,
    icon: '/icons/bookmark.svg',
    label: 'Na fila',
  },
  [GameStatusEnum.PLAYING]: {
    id: 2,
    value: GameStatusEnum.PLAYING,
    icon: '/icons/controller.svg',
    label: 'Jogando',
  },
  [GameStatusEnum.FINISHED]: {
    id: 3,
    value: GameStatusEnum.FINISHED,
    icon: '/icons/flag_check.svg',
    label: 'Finalizado',
  },
  [GameStatusEnum.MAXED_OUT]: {
    id: 4,
    value: GameStatusEnum.MAXED_OUT,
    icon: '/icons/trophy.svg',
    label: 'Platinado',
  },
  [GameStatusEnum.ABANDONED]: {
    id: 5,
    value: GameStatusEnum.ABANDONED,
    icon: '/icons/cancel.svg',
    label: 'Abandonado',
  },
};
