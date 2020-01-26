import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { Order, OrderLine, OrderStatus } from '../store/orders.model';
import { Observable } from 'rxjs';
import { OrderLinesSelectors } from '../store/order-lines.selector';
import { OrdersService } from '../orders.service';
import { OrderLinesActions } from '../store/order-lines.actions';
import { OrderLinesService } from '../order-lines.service';
import { OrdersActions } from '../store/orders.actions';
import { map, tap, filter } from 'rxjs/operators';
import { OrdersSelectors } from '../store/orders.selector';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent implements OnInit {

  order$: Observable<Order> = this.store.select(OrdersSelectors.selectOrder(this.route.snapshot.params.orderId)).pipe(
    filter(o => o !== undefined),
  );
  orderName$: Observable<string> = this.order$.pipe(
    map(order => order.name),
  );
  orderLines$: Observable<OrderLine[]> = this.store.select(OrderLinesSelectors.selectAllOrderLines);


  constructor(
    private route: ActivatedRoute,
    private store: Store<{}>,
    private service: OrdersService,
    private orderLineService: OrderLinesService,
  ) { }

  ngOnInit() {
    this.store.dispatch(OrdersActions.orderRequested({ id: this.orderId }));
    this.store.dispatch(OrderLinesActions.linesRequested({ id: this.orderId }));
  }

  releaseOrder() {
    this.service.releaseOrder(this.orderId)
      .subscribe(
        ({ id }) => this.store.dispatch(OrdersActions.orderReleased({ id })),
        error => alert(error.error.message),
      );
  }

  removeOrderLine({ id }: OrderLine) {
    this.orderLineService.removeOrderLine(this.orderId, id)
    .subscribe(
      () => this.store.dispatch(OrderLinesActions.orderLineRemoved({ id })),
      error => alert(error.error.message),
    );
  }

  get orderId() {
    return this.route.snapshot.params.orderId;
  }

}
