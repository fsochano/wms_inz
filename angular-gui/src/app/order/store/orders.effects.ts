import { OrdersActions } from './orders.actions';
import { createEffect, ofType, Actions } from '@ngrx/effects';
import { switchMap, map, withLatestFrom, filter } from 'rxjs/operators';
import { OrdersService } from '../orders.service';
import { AppState } from 'src/app/app.reducer';
import { Store } from '@ngrx/store';
import { OrdersSelectors } from './orders.selector';

export class OrdersEffects {
    loadOrders$ = createEffect(() =>
        this.actions$.pipe(
            ofType(OrdersActions.loadOrders),
            withLatestFrom(this.store.select(OrdersSelectors.selectAreAllOrdersLoaded)),
            filter(([, loaded]) => !loaded),
            switchMap(() => this.ordersService.loadOrders()),
            map(orders => OrdersActions.ordersLoaded({ orders })),
        )
    );

    constructor(
        private actions$: Actions,
        private ordersService: OrdersService,
        private store: Store<AppState>,
    ) { }
}
