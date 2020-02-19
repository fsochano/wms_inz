import { createReducer, on } from '@ngrx/store';
import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { OrdersActions } from './orders.actions';
import { Order, OrderStatus } from './orders.model';

export interface OrdersState extends EntityState<Order> {
 }

export const OrdersAdapter: EntityAdapter<Order> = createEntityAdapter<Order>();

const initialState: OrdersState = OrdersAdapter.getInitialState();

export const OrdersReducers = createReducer(
    initialState,
    on(OrdersActions.ordersRequested,
        (state) => OrdersAdapter.removeAll(state)),
    on(OrdersActions.ordersLoaded,
        (state, { orders }) => OrdersAdapter.addAll(orders, state)),
    on(OrdersActions.orderLoaded,
        (state, { order }) => OrdersAdapter.addOne(order, state)),
    on(OrdersActions.orderCreated,
        (state, { order }) => OrdersAdapter.addOne(order, state)),
    on(OrdersActions.orderRemoved,
        (state, { id }) => OrdersAdapter.removeOne(id, state)),
    on(OrdersActions.orderReleased,
        (state, { id }) => OrdersAdapter.updateOne({ id, changes: { status: OrderStatus.RELEASED } }, state))
);
