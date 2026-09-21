export interface ErrorMessageDto {
  message: string;
  httpStatus: string;
  field: string | null;
}
