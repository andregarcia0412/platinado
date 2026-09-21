import { HttpErrorResponse } from '@angular/common/http';
import { BadRequestError } from './bad-request.error';
import { ConflictError } from './conflict.error';
import { InternalServerError } from './internal-server.error';
import { UnauthorizedError } from './unauthorized.error';
import { UnexpectedError } from './unexpected.error';
import { ErrorMessageDto } from './error-dto';

const messages: Record<string, string> = {
  '400': 'Verifique os dados informados',
  '401': 'Usuário ou senha inválidos',
  '409:email': 'Esse e-mail já está em uso',
  '409:username': 'Esse nome de usuário já está em uso',
  '409': 'Esse registro já existe',
};

const messageFor = (status: number, field: string | null): string =>
  (field && messages[`${status}:${field}`]) ??
  messages[status] ??
  `Erro inesperado (${status}). Tente novamente`;

export const translateHttpError = (e: unknown): never => {
  if (!(e instanceof HttpErrorResponse)) {
    throw new UnexpectedError(`Error inesperado. Tente novamente`);
  }

  if (e.status === 0) throw new InternalServerError('Erro de conexão com o servidor');
  if (e.status >= 500)
    throw new InternalServerError('Erro no servidor. Tente novamente mais tarde');

  const field = (e.error as Partial<ErrorMessageDto> | null)?.field ?? null;
  const message = messageFor(e.status, field);

  switch (e.status) {
    case 400:
      throw new BadRequestError(message, field);
    case 401:
      throw new UnauthorizedError(message);
    case 409:
      throw new ConflictError(message, field);
    default:
      throw new UnexpectedError(message);
  }
};
