import { Component, input } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-avatar',
  templateUrl: './avatar.html',
})
export class Avatar {
  readonly image = input<string>('/icons/person.svg');
}
