
import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthSelectors } from './auth.selectror';
import { Store } from '@ngrx/store';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {

  constructor(private store: Store<{}>, private router: Router) {
  }

  canActivate(): Observable<boolean | UrlTree> {
    return this.store.select(AuthSelectors.isLoggedIn)
      .pipe(
        map(isLoggedIn => {
          return isLoggedIn || this.router.createUrlTree(['login']);
        })
      );
  }
}
