import { PickTasksService } from './../pick-tasks.service';
import { createEffect, ofType, Actions } from '@ngrx/effects';
import { PickTasksActions } from './pick-tasks.actions';
import { switchMap, map } from 'rxjs/operators';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class PickTasksEffects {

    loadPickTasks$ = createEffect(() =>
        this.actions$.pipe(
            ofType(PickTasksActions.pickTaksRequested),
            switchMap(({ pickListId }) => this.pickTasksService.loadPickTasks(pickListId)),
            map(pickTasks => PickTasksActions.pickTasksLoaded({ pickTasks })),
        ));

    constructor(
        private actions$: Actions,
        private pickTasksService: PickTasksService,
    ) { }
}
