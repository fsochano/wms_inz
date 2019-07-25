import { createReducer, on } from '@ngrx/store';
import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { OrdersActions } from './orders.actions';
import { Order } from './orders.model';

export interface OrdersState extends EntityState<Order> {
    allOrdersLoaded: boolean;
 }

export const OrdersAdapter: EntityAdapter<Order> = createEntityAdapter<Order>();

const initialState: OrdersState = OrdersAdapter.getInitialState({
    allOrdersLoaded: false,
});

export const OrdersReducers = createReducer(
    initialState,
    on(OrdersActions.ordersLoaded,
        (state, { orders }) => OrdersAdapter.addAll(orders, {...state, allOrdersLoaded: true })),
    on(OrdersActions.orderLoaded,
        (state, { order }) => OrdersAdapter.addOne(order, state)),
    on(OrdersActions.orderCreated,
        (state, { order }) => OrdersAdapter.addOne(order, state)),
    on(OrdersActions.removeOrder,
        (state, { id }) => OrdersAdapter.removeOne(id, state))
);
