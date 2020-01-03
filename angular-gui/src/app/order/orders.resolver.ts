import { OrdersActions } from './store/orders.actions';
import { OrdersSelectors } from './store/orders.selector';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Order } from './store/orders.model';
import { Observable, iif, of } from 'rxjs';
import { AppState } from '../app.reducer';
import { Store } from '@ngrx/store';
import { tap, filter, first, switchMap } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { OrdersService } from './orders.service';

@Injectable({
    providedIn: 'root',
})
export class OrderResolver implements Resolve<Order> {
    constructor(
        private readonly store: Store<AppState>,
        private readonly service: OrdersService,
    ) { }

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<Order> {
        this.store.dispatch(OrdersActions.loadOrder({ id: route.params.orderId }))
        return this.store.select(OrdersSelectors.selectOrder(route.params.orderId)).pipe(first());
    }
}
