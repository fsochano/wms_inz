import { PickList } from '../pick-lists.model';
import { createAction, props } from '@ngrx/store';

class Actions{

    readonly pickListsRequested = createAction(
        '[Pick Lists component] Pick Lists Requested',
    );

    readonly pickListsLoaded = createAction(
        '[Pick Lists component] Pick Lists Loaded',
        props<{ pickLists: PickList[] }>(),
    );

}

export const PickListsActions = new Actions();