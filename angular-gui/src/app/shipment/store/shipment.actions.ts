import { createAction, props } from '@ngrx/store';
import { Shipment } from '../shipment.model';

class Actions{

    readonly shipmentRequested = createAction(
        '[Shipment component] Shipment Requested',
    );

    readonly shipmentLoaded = createAction(
        '[Shipment component] Shipment Loaded',
        props<{ shipments: Shipment[] }>(),
    );

    readonly shipmentUpdated = createAction(
        '[Shipment component] Shipment Updated',
        props<{ shipment: Shipment }>(),
    );

}

export const ShipmentActions = new Actions();
