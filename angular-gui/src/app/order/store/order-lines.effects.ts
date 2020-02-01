import { OrdersActions } from './orders.actions';
import { createEffect, ofType, Actions } from '@ngrx/effects';
import { switchMap, map } from 'rxjs/operators';
import { OrderLinesActions } from './order-lines.actions';
import { OrderLinesService } from '../order-lines.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class OrderLinesEffects {
  loadOrderLines$ = createEffect(() =>
    this.actions$.pipe(
      ofType(
        OrderLinesActions.linesRequested,
        OrdersActions.orderReleased,
      ),
      switchMap(({ id }) => this.service.loadOrderLines(id)),
      map(lines => OrderLinesActions.orderLinesLoaded({ lines })),
    )
  );

  constructor(
    private actions$: Actions,
    private service: OrderLinesService,
  ) { }
}
