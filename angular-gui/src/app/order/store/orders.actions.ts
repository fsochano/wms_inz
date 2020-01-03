import { Order } from './orders.model';
import { createAction, props } from '@ngrx/store';

class Actions {
    readonly loadOrders = createAction(
        '[Order component] load orders',
    );
    readonly ordersLoaded = createAction(
        '[Orders Component] orders loaded',
        props<{ orders: Order[] }>(),
    );
    readonly orderCreated = createAction(
        '[Order component] order created',
        props<{ order: Order}>(),
    );
    readonly loadOrder = createAction(
        '[Order component] load order',
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
    )
}

export const OrdersActions = new Actions();
