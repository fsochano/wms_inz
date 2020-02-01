import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AuthSelectors } from './auth.selectror';
import { map } from 'rxjs/operators';
import { getRedirectPath } from '../app-routing-helper';

@Injectable({
  providedIn: 'root',
})
export class LoginGuard implements CanActivate {

  constructor(private store: Store<{}>, private router: Router) {
  }

  canActivate(): Observable<boolean | UrlTree> {
    return this.store.select(AuthSelectors.user)
      .pipe(
        map(user => {
          return user === undefined || this.router.createUrlTree([getRedirectPath(user.authorities)]);
        })
      );
  }
}
