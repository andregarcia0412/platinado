export interface ReturnGameDto {
  id: number;
  name: string;
  slug: string;
  summary: string | null;
  firstReleaseDate: string | null;
  coverUrl: string | null;
  gameType: ReturnGameTypeDto;
  createdAt: string;
}

export interface ReturnGameTypeDto {
  id: number;
  type: string;
}
