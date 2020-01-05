import { Router } from '@angular/router';
import { OrdersActions } from './store/orders.actions';
import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from '../app.reducer';
import { Observable } from 'rxjs';
import { AuthSelectors } from '../auth/auth.selectror';
import { Order } from './store/orders.model';
import { OrdersService } from './orders.service';
import { OrdersSelectors } from './store/orders.selector';

@Component({
  selector: 'app-order',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class OrderComponent implements OnInit {

  orders$: Observable<Order[]> = this.store.select(OrdersSelectors.selectAllOrders);
  user$: Observable<string> = this.store.select(AuthSelectors.userName);

  constructor(
    private readonly store: Store<AppState>,
    private readonly service: OrdersService,
  ) { }

  ngOnInit() {
    // this.service.loadOrders()
    // .subscribe(orders => this.store.dispatch(OrdersActions.ordersLoaded({ orders })));
    this.store.dispatch(OrdersActions.ordersRequested());
  }

  removeOrder({ id }: Order) {
    this.service.removeOrder(id).subscribe(
      () => this.store.dispatch(OrdersActions.orderRemoved({ id }))
    );
  }
}
