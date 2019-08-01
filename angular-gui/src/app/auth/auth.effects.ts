import { Action } from '@ngrx/store';
import { AuthActions } from './auth.action';
import { createEffect, Actions, ofType, ROOT_EFFECTS_INIT, OnInitEffects } from '@ngrx/effects';
import { tap, map } from 'rxjs/operators';

export class AuthEffects implements OnInitEffects {

    save$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AuthActions.login),
            tap((state) =>  localStorage.setItem('authState', JSON.stringify(state)))
        ),
        { dispatch: false }
    );

    clear$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AuthActions.logout),
            tap(() => localStorage.removeItem('authState'))
        ),
        { dispatch: false, }
    );

    constructor(
        private actions$: Actions,
    ) { }


    ngrxOnInitEffects(): Action {
        const userData = localStorage.getItem('authState');
        if (userData) {
            return AuthActions.login(JSON.parse(userData));
        } else {
            return AuthActions.logout();
        }
    }

}
