import { createEffect, ofType, Actions } from '@ngrx/effects';
import { map, switchMap } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { AppUsersActions } from './users.actions';
import { UsersService } from '../users.service';

@Injectable({
  providedIn: 'root',
})
export class UsersEffects {

    loadUsers$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AppUsersActions.loadUsers),
            switchMap(() => this.service.loadUsers()),
            map(users => AppUsersActions.usersLoaded({ users })),
        ));

    constructor(
        private readonly actions$: Actions,
        private readonly service: UsersService,
    ) { }
}
