import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'range',
})
export class RangePipe implements PipeTransform {
  transform(n: number): unknown[] {
    return Array.from({ length: n });
  }
}
