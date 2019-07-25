import { Action } from '@ngrx/store';
import { AuthActions } from './auth.action';
import { createEffect, Actions, ofType, ROOT_EFFECTS_INIT, OnInitEffects } from '@ngrx/effects';
import { tap, map } from 'rxjs/operators';

export class AuthEffects implements OnInitEffects {

    save$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AuthActions.login),
            tap(({ user }) =>  localStorage.setItem('user', JSON.stringify(user)))
        ),
        { dispatch: false }
    );

    clear$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AuthActions.logout),
            tap(() => localStorage.removeItem('user'))
        ),
        { dispatch: false, }
    );

    constructor(
        private actions$: Actions,
    ) { }


    ngrxOnInitEffects(): Action {
        const userData = localStorage.getItem('user');
        if (userData) {
            return AuthActions.login({ user: JSON.parse(userData) });
        } else {
            return AuthActions.logout();
        }
    }

}
