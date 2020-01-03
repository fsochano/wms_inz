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

  orders$: Observable<Order[]>;
  user$: Observable<string>;

  constructor(
    private readonly store: Store<AppState>,
    private readonly service: OrdersService,
  ) { }

  ngOnInit() {
    this.orders$ = this.store.select(OrdersSelectors.selectAllOrders);
    this.user$ = this.store.select(AuthSelectors.userName);

    this.store.dispatch(OrdersActions.loadOrders());
  }

  removeOrder({ id }: Order) {
    this.service.removeOrder(id).subscribe(
      () => this.store.dispatch(OrdersActions.orderRemoved({ id }))
    );
  }
}
