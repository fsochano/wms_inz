import { AuthService } from './auth/auth.service';
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

  showOrdering = this.store.select(AuthSelectors.hasAuthority, 'ORDERING');
  showPicking = this.store.select(AuthSelectors.hasAuthority, 'PICKING');
  showShipping = this.store.select(AuthSelectors.hasAuthority, 'SHIPPING');
  showSettings = this.store.select(AuthSelectors.hasAuthority, 'SETTINGS');

  constructor(
    private store: Store<{}>,
    private authService: AuthService,
  ) {
  }

  ngOnInit(): void {
  }

  logout() {
    this.store.dispatch(AuthActions.logout());
  }

}
