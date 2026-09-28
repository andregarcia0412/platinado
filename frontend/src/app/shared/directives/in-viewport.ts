import { afterNextRender, DestroyRef, Directive, ElementRef, inject, output } from '@angular/core';

@Directive({ selector: '[appInViewport]' })
export class InViewport {
  readonly inViewport = output<void>();
  private readonly element = inject<ElementRef<HTMLElement>>(ElementRef);

  constructor() {
    const observer = new IntersectionObserver(
      ([entry]) => entry.isIntersecting && this.inViewport.emit(),
      { rootMargin: '400px' },
    );
    afterNextRender(() => observer.observe(this.element.nativeElement));
    inject(DestroyRef).onDestroy(() => observer.disconnect());
  }
}
