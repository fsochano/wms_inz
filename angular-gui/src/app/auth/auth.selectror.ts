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
        user => user ? user.name : '',
    );
}

export const AuthSelectors = new Selectors();
