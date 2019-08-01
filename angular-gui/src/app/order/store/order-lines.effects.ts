import { createEffect, ofType, Actions } from '@ngrx/effects';
import { OrderLinesActions } from './order-lines.actions';
import { withLatestFrom, filter, switchMap, map, tap } from 'rxjs/operators';
import { OrderLinesSelectors } from './order-lines.selector';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/app.reducer';
import { OrderLinesService } from '../order-lines.service';

export class OrderLinesEffects {
    loadOrderLines$ = createEffect(() =>
        this.actions$.pipe(
            ofType(OrderLinesActions.linesRequested),
            withLatestFrom(this.store.select(OrderLinesSelectors.areLinesLoaded)),
            filter(([, loaded]) => !loaded),
            switchMap(([{id}]) => this.service.loadOrderLines(id)),
            map(lines => OrderLinesActions.orderLinesLoaded({ lines })),
        )
    );

    constructor(
        private actions$: Actions,
        private service: OrderLinesService,
        private store: Store<AppState>,
    ) { }
}
