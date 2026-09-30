import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateToString',
})
export class DateToStringPipe implements PipeTransform {
  transform(date: Date): string {
    return date.toLocaleDateString('pt-br', {
      year: 'numeric',
      day: 'numeric',
      month: 'long',
    });
  }
}
