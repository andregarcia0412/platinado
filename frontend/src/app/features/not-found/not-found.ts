import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { OutlinedButton } from '../../shared/components/outlined-button/outlined-button';

@Component({
  imports: [OutlinedButton],
  selector: 'app-not-found',
  templateUrl: './not-found.html',
})
export class NotFound {
  private readonly router = inject(Router);
  readonly currentUrl = this.router.url;

  onReturnClick() {
    this.router.navigate(['/catalogue']);
  }
}
