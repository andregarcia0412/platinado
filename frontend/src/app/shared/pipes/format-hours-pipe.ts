import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatHours',
})
export class FormatHoursPipe implements PipeTransform {
  transform(value: number): string {
    return String(value).replace('.', ',') + ' h';
  }
}
