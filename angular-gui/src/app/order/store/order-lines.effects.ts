import { createEffect, ofType, Actions } from '@ngrx/effects';
import { switchMap, map } from 'rxjs/operators';
import { Store } from '@ngrx/store';
import { AppState } from '../../app.reducer';
import { OrderLinesActions } from './order-lines.actions';
import { OrderLinesService } from '../order-lines.service';

export class OrderLinesEffects {
    loadOrderLines$ = createEffect(() =>
        this.actions$.pipe(
            ofType(OrderLinesActions.linesRequested),
            switchMap(({ id }) => this.service.loadOrderLines(id)),
            map(lines => OrderLinesActions.orderLinesLoaded({ lines })),
        )
    );

    constructor(
        private actions$: Actions,
        private service: OrderLinesService,
        private store: Store<AppState>,
    ) { }
}
