export class BadRequestError extends Error {
  constructor(
    message: string,
    readonly field: string | null = null,
  ) {
    super(message);
    this.name = 'BadRequestError';
  }
}
