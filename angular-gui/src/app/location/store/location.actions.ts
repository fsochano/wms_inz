import { WarehouseLocation } from './location.model';
import { createAction, props } from '@ngrx/store';

class Actions {
    readonly locationsRequested = createAction(
        '[Locations] Location requested',
    );
    readonly locationsLoaded = createAction(
        '[Locations] Locations loaded',
        props<{ locations: WarehouseLocation[] }>(),
    );
    readonly locationCreated = createAction(
        '[Locations] Location created',
        props<{ location: WarehouseLocation }>(),
    );

    readonly locationsContainersRequested = createAction(
        '[Locations] Location containers requested',
        props<{ locationId: number }>(),
    );
}

export const LocationsActions = new Actions();
