
import { createEffect, ofType, Actions } from '@ngrx/effects';
import { switchMap, map, withLatestFrom } from 'rxjs/operators';
import { Store } from '@ngrx/store';
import { OrdersSelectors } from './orders.selector';
import { OrdersService } from '../orders.service';
import { OrdersActions } from './orders.actions';

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
      switchMap(({ id }) => this.ordersService.loadOrder(id)),
      map(order => OrdersActions.orderLoaded({ order })),
    ));

  constructor(
    private actions$: Actions,
    private ordersService: OrdersService,
    private store: Store<{}>,
  ) { }
}
