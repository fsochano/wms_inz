import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from '../app.reducer';
import { AuthorityGuard } from '../auth/authority.guard';

@Injectable({
  providedIn: 'root',
})
export class OrderingGuard extends AuthorityGuard {

  constructor(store: Store<AppState>) {
    super('ORDERING', store);
  }

}