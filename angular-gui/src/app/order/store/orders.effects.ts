
import { createEffect, ofType, Actions } from '@ngrx/effects';
import { switchMap, map, withLatestFrom, filter } from 'rxjs/operators';
import { Store } from '@ngrx/store';
import { OrdersSelectors } from './orders.selector';
import { OrdersService } from '../orders.service';
import { OrdersActions } from './orders.actions';
import { AppState } from '../../app.reducer';

export class OrdersEffects {
  loadOrders$ = createEffect(() =>
    this.actions$.pipe(
      ofType(OrdersActions.ordersRequested),
      switchMap(() => this.ordersService.loadOrders()),
      map(orders => OrdersActions.ordersLoaded({ orders })),
    )
  );

  loadOrder$ = createEffect(() =>
    this.actions$.pipe(
      ofType(OrdersActions.orderRequested),
      withLatestFrom(this.store.select(OrdersSelectors.selectOrdersMap)),
      switchMap(([{ id }]) => this.ordersService.loadOrder(id)),
      map(order => OrdersActions.orderLoaded({ order })),
    ));

  constructor(
    private actions$: Actions,
    private ordersService: OrdersService,
    private store: Store<AppState>,
  ) { }
}
