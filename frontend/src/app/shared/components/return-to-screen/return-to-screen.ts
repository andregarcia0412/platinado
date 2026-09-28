import { Component, inject, input } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  imports: [],
  selector: 'app-return-to-screen',
  templateUrl: './return-to-screen.html',
})
export class ReturnToScreen {
  private readonly location = inject(Location);
  readonly text = input.required<string>();

  goBack() {
    this.location.back();
  }
}
