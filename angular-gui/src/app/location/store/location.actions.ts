import { WarehouseLocation } from './location.model';
import { createAction, props } from '@ngrx/store';

class Actions {
    readonly locationsLoaded = createAction(
        '[Locations] Locations loaded',
        props<{ locations: WarehouseLocation[] }>(),
    );
    readonly locationCreated = createAction(
        '[Locations] Location created',
        props<{ location: WarehouseLocation }>(),
    );
}

export const LocationsActions = new Actions();
