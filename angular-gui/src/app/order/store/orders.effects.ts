
import { createEffect, ofType, Actions } from '@ngrx/effects';
import { switchMap, map } from 'rxjs/operators';
import { Store } from '@ngrx/store';
import { OrdersService } from '../orders.service';
import { OrdersActions } from './orders.actions';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
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
