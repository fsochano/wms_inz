import { Authority } from './auth.model';
import { AuthState } from './auth.readucer';
import { createSelector, createFeatureSelector } from '@ngrx/store';

class Selectors {
    state = createFeatureSelector<AuthState>('authFeature');

    isLoggedIn = createSelector(
        this.state,
        state => state.user !== undefined,
    );
    user = createSelector(
        this.state,
        state => state.user,
    );
    userName = createSelector(
        this.user,
        user => user ? user.username : '',
    );

    token = createSelector(
        this.user,
        user => user && user.token,
    );

    authorities = createSelector(
        this.user,
        user => user && user.authorities,
    );

    authoritiesSet = createSelector(
      this.authorities,
      authorities => new Set(authorities),
  );

    hasAuthority = createSelector(
      this.authoritiesSet,
      (authorities: Set<Authority>, authority: Authority) => authorities.has(authority),
    );
}

export const AuthSelectors = new Selectors();
