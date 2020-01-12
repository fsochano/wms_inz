import { WarehouseLocation } from './location.model';
import { createAction, props } from '@ngrx/store';

class Actions {
    readonly locationsLoaded = createAction(
        '[Locations Component] Locations loaded',
        props<{ locations: WarehouseLocation[] }>(),
    );
}

export const LocationsActions = new Actions();
