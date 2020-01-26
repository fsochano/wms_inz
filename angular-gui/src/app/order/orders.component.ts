import { ColumnSchema } from './../shared/column-schema.model';
import { OrdersActions } from './store/orders.actions';
import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AuthSelectors } from '../auth/auth.selectror';
import { Order, OrderStatus } from './store/orders.model';
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

  readonly columnSchema: ColumnSchema<Order>[] = [
    { name: 'Order Name', param: 'name' },
    { name: 'Order Status', param: 'status' },
    { name: 'Last Change By', param: 'lastChangeBy' },
    { name: 'Last Change Date', param: 'lastChangeDate' },
  ];
  readonly displayedColumns = [...this.columnSchema.map(s => s.param), 'bt-actions'];

  constructor(
    private readonly store: Store<{}>,
    private readonly service: OrdersService,
  ) { }

  ngOnInit() {
    // this.service.loadOrders() 
    // .subscribe(orders => this.store.dispatch(OrdersActions.ordersLoaded({ orders })));
    this.store.dispatch(OrdersActions.ordersRequested());

  }

  removeOrder({ id }: Order) {
    this.service.removeOrder(id).subscribe(
      () => this.store.dispatch(OrdersActions.orderRemoved({ id })),
      error => alert(error.error.message),
    );
  }

  
  trackById(order?: Order) {
    return order && order.id;
  }

  isDisabled({ status }: Order) {
    return status !== OrderStatus.HOLD;
  }

}
