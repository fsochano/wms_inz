import { AppState } from './../app.reducer';
import {Injectable} from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import { AuthSelectors } from './auth.selectror';
import { Store } from '@ngrx/store';

@Injectable({
    providedIn: 'root',
})
export class AuthGuard implements CanActivate {

  constructor(private store: Store<AppState>, private router: Router) {
  }

  canActivate(): Observable<boolean>  {
    return this.store.select(AuthSelectors.isLoggedIn)
      .pipe(
        tap(isLoggedIn => {
          if (!isLoggedIn) {
            void this.router.navigateByUrl('/login');
          }
        })
    );
  }
}