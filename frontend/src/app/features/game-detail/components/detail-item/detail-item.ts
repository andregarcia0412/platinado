import { Component, input } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-detail-item',
  templateUrl: './detail-item.html',
  host: { class: 'col-span-2 grid grid-cols-subgrid items-center' },
})
export class DetailItem {
  readonly label = input.required<string>();
  readonly detail = input.required<string>();
}
