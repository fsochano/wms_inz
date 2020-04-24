import { AuthService } from './auth.service';

import { createEffect, ofType, OnInitEffects, Actions } from '@ngrx/effects';
import { Action } from '@ngrx/store';
import { AuthActions } from './auth.action';
import { tap, switchMap } from 'rxjs/operators';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class AuthEffects implements OnInitEffects {

    save$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AuthActions.login),
            tap((state) => localStorage.setItem('authState', JSON.stringify(state))),
        ),
        { dispatch: false }
    );

    clear$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AuthActions.logout),
            switchMap(() => this.authService.logout()),
            tap(() => localStorage.removeItem('authState')),
        ),
        { dispatch: false, }
    );

    constructor(
        private actions$: Actions,
        private authService: AuthService,
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
