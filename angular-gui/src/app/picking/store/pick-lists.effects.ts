import { PickListsActions } from './pick-lists.actions';
import { PickListService } from '../pick-list.service';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { switchMap, map } from 'rxjs/operators';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class PickListsEffects {

    loadPickLists$ = createEffect(() =>
        this.actions$.pipe(
            ofType(PickListsActions.pickListsRequested),
            switchMap(() => this.pickListService.loadPicking()),
            map(pickLists => PickListsActions.pickListsLoaded({ pickLists })),
        ));

    constructor(
        private actions$: Actions,
        private pickListService: PickListService,
    ) { }
}
