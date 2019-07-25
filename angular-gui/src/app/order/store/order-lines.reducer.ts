import { OrderLinesActions } from './order-lines.actions';
import { OrderLine } from './orders.model';
import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { createReducer, on } from '@ngrx/store';

export interface OrderLinesState extends EntityState<OrderLine> { }

export const OrderLinesAdapter: EntityAdapter<OrderLine> = createEntityAdapter<OrderLine>();

const initialState: OrderLinesState = OrderLinesAdapter.getInitialState({});

export const OrderLinesReducers = createReducer(
    initialState,
    on(OrderLinesActions.orderLinesLoaded,
        (state, { lines }) => OrderLinesAdapter.addAll(lines, state)),
    on(OrderLinesActions.orderLineCreated,
        (state, { line }) => OrderLinesAdapter.addOne(line, state))
);
