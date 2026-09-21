export class ConflictError extends Error {
  constructor(
    message: string,
    readonly field: string | null = null,
  ) {
    super(message);
    this.name = 'ConflictError';
  }
}
