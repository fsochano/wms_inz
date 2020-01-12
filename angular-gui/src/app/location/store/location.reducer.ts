import { LocationsActions } from './location.actions';
import { WarehouseLocation } from './location.model';
import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { createReducer, on } from '@ngrx/store';

export interface LocationsState extends EntityState<WarehouseLocation> {
}

export const LocationsAdapter: EntityAdapter<WarehouseLocation> = createEntityAdapter<WarehouseLocation>();

const initialState: LocationsState = LocationsAdapter.getInitialState();

export const LocationsReducer = createReducer(
    initialState,
    on(LocationsActions.locationsLoaded,
        (state, { locations }) => LocationsAdapter.addAll(locations, state)),
);