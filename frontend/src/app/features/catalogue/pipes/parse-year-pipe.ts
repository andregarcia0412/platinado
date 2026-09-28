import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'parseYear',
})
export class ParseYearPipe implements PipeTransform {
  transform(value: string | null | undefined): string | undefined {
    if (!value) return undefined;
    return value.slice(0, 4);
  }
}
