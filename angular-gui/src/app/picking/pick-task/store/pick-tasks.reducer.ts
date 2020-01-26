import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { createReducer, on } from '@ngrx/store';
import { PickTask } from '../pick-tasks.model';
import { PickTasksActions } from './pick-tasks.actions';

export interface PickTasksState extends EntityState<PickTask> {
}

export const PickTasksAdapter: EntityAdapter<PickTask> = createEntityAdapter<PickTask>();

const initialState: PickTasksState = PickTasksAdapter.getInitialState({});

export const PickTasksReducers = createReducer(
    initialState,
    on(PickTasksActions.pickTaksRequested,
        state => PickTasksAdapter.removeAll(state)),
    on(PickTasksActions.pickTasksLoaded,
        (state, { pickTasks }) => PickTasksAdapter.addAll(pickTasks, state)),
    on(PickTasksActions.pickTaskUpdated,
        (state, { pickTask }) => PickTasksAdapter.upsertOne(pickTask, state)),
);
