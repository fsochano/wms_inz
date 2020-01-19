import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { createReducer, on } from '@ngrx/store';
import { Container } from './containers.model';
import { ContainersActions } from './containers.actions';

export interface ContainersState extends EntityState<Container> {
}

export const ContainersAdapter: EntityAdapter<Container> = createEntityAdapter<Container>();

const initialState: ContainersState =ContainersAdapter.getInitialState();

export const ContainersReducer = createReducer(
    initialState,
    on(ContainersActions.containersLoaded,
        (state, { containers }) => ContainersAdapter.addAll(containers, state)),
    on(ContainersActions.containerCreated,
        (state, { container }) => ContainersAdapter.addOne(container, state)),
);