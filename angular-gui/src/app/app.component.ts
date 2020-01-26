import { AuthActions } from './auth/auth.action';
import { AuthSelectors } from './auth/auth.selectror';
import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  userName$: Observable<string> = this.store.select(AuthSelectors.userName);
  isUserLogged$: Observable<boolean> = this.store.select(AuthSelectors.isLoggedIn);

  constructor(
    private store: Store<{}>
  ) {
  }

  ngOnInit(): void {
  }

  logout() {
    this.store.dispatch(AuthActions.logout());
  }

}
