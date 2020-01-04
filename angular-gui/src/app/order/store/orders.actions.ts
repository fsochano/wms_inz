import { Order } from './orders.model';
import { createAction, props } from '@ngrx/store';

class Actions {
    readonly ordersRequested = createAction(
        '[Order component] orders Requested',
    );
    readonly ordersLoaded = createAction(
        '[Orders Component] orders loaded',
        props<{ orders: Order[] }>(),
    );
    readonly orderCreated = createAction(
        '[Order component] order created',
        props<{ order: Order}>(),
    );
    readonly orderRequested = createAction(
        '[Order component] order requested',
        props<{ id: number }>(),
    );
    readonly orderLoaded = createAction(
        '[Order component] order loaded',
        props<{ order: Order }>(),
    );
    readonly orderRemoved = createAction(
        '[Order component] order removed',
        props<{ id: number }>(),
    );
    readonly orderReleased = createAction(
        '[Order component] order released',
        props<{ id: number }>(),
    );
}

export const OrdersActions = new Actions();
