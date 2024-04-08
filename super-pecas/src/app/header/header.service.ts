import { Injectable } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  public headerTitle: BehaviorSubject<string> = new BehaviorSubject<string>('');

  constructor(private router: Router) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.setHeaderTitle();
    });
  }

  private setHeaderTitle(): void {
    const currentRoute = this.router.routerState.snapshot.root;
    const title = this.getTitleFromRoute(currentRoute);
    this.headerTitle.next(title || '');
  }

  private getTitleFromRoute(route: any): string {
    if (route.children && route.children.length) {
      return this.getTitleFromRoute(route.children[0]);
    }
    return route.data ? route.data.title : null;
  }
}
