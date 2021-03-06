import { CanActivate } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AuthSelectors } from '../auth/auth.selectror';
import { map } from 'rxjs/operators';
import { Authority } from './auth.model';

export class AuthorityGuard implements CanActivate {

  constructor(
    private readonly authority: Authority,
    private readonly store: Store<{}>
  ) {
  }

  canActivate(): Observable<boolean> {
    return this.store.select(AuthSelectors.authorities)
      .pipe(
        map(authorities => {
          return new Set(authorities).has(this.authority);
        }),
      );
  }
}
