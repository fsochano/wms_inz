import { createAction, props } from '@ngrx/store';
import { OrderLine } from './orders.model';

class Actions {

    readonly linesRequested = createAction(
        '[Order Line component] order lines requested',
        props<{ id: number }>(),
    );

    readonly orderLinesLoaded = createAction(
        '[Orders Lines Component] order lines loaded',
        props<{ lines: OrderLine[] }>(),
    );

    readonly orderLineCreated = createAction(
        '[Order Line Component] order line created',
        props<{ line: OrderLine }>(),
    );

    readonly orderLineRemoved = createAction(
        '[Order Line component] remove order line',
        props<{ id: number }>(),
    );

}

export const OrderLinesActions = new Actions();
