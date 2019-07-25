import { AuthActions } from './auth/auth.action';
import { AuthSelectors } from './auth/auth.selectror';
import { Component, OnInit } from '@angular/core';
import { AppState } from './app.reducer';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { map, startWith, filter } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  userName$: Observable<string>;
  isUserLogged$: Observable<boolean>;

  constructor(
    private store: Store<AppState>
  ) {
  }

  ngOnInit(): void {
    this.userName$ = this.store.select(AuthSelectors.userName);
    this.isUserLogged$ = this.store.select(AuthSelectors.isLoggedIn);
  }

  logout() {
    this.store.dispatch(AuthActions.logout());
  }

}
