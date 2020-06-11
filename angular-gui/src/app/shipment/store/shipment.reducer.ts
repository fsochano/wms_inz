import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { createReducer, on } from '@ngrx/store';
import { Shipment } from '../shipment.model';
import { ShipmentActions } from './shipment.actions';

export interface ShipmentState extends EntityState<Shipment> {
 }

export const ShipmentAdapter: EntityAdapter<Shipment> = createEntityAdapter<Shipment>();

const initialState: ShipmentState = ShipmentAdapter.getInitialState({});

export const ShipmentReducers = createReducer(
    initialState,
    on(ShipmentActions.shipmentRequested,
        state => ShipmentAdapter.removeAll(state)),
    on(ShipmentActions.shipmentLoaded,
        (state, { shipments }) => ShipmentAdapter.addAll(shipments, state)),
    on(ShipmentActions.shipmentUpdated,
        (state, { shipment }) => ShipmentAdapter.upsertOne(shipment, state)),
);
