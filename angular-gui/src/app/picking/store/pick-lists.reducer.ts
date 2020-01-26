import { PickListsActions } from './pick-lists.actions';
import { PickList } from '../pick-lists.model';
import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { createReducer, on } from '@ngrx/store';

export interface PickListsState extends EntityState<PickList> {
 }

export const PickListsAdapter: EntityAdapter<PickList> = createEntityAdapter<PickList>();

const initialState: PickListsState = PickListsAdapter.getInitialState({});

export const PickListsReducers = createReducer(
    initialState,
    on(PickListsActions.pickListsRequested,
        state => PickListsAdapter.removeAll(state)),
    on(PickListsActions.pickListsLoaded,
        (state, { pickLists }) => PickListsAdapter.addAll(pickLists, state)),
);
