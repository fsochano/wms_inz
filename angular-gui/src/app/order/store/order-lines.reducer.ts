import { OrderLinesActions } from './order-lines.actions';
import { OrderLine } from './orders.model';
import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { createReducer, on } from '@ngrx/store';

export interface OrderLinesState extends EntityState<OrderLine> {
    orderId: number;
    isLoaded: boolean;
 }

export const OrderLinesAdapter: EntityAdapter<OrderLine> = createEntityAdapter<OrderLine>();

const initialState: OrderLinesState = OrderLinesAdapter.getInitialState({
    orderId: -1,
    isLoaded: false,
});

export const OrderLinesReducers = createReducer(
    initialState,
    on(OrderLinesActions.linesRequested,
        (state, { id }) => state.orderId !== id ? OrderLinesAdapter.removeAll({ ...state, orderId: id, isLoaded: false }) : state),
    on(OrderLinesActions.orderLinesLoaded,
        (state, { lines }) => OrderLinesAdapter.addAll(lines, { ...state, isLoaded: true })),
    on(OrderLinesActions.orderLineCreated,
        (state, { line }) => OrderLinesAdapter.addOne(line, state))
);
