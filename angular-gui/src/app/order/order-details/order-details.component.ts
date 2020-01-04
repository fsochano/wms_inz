import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState } from '../../app.reducer';
import { Order, OrderLine, OrderStatus } from '../store/orders.model';
import { Observable } from 'rxjs';
import { OrderLinesSelectors } from '../store/order-lines.selector';
import { OrdersService } from '../orders.service';
import { OrderLinesActions } from '../store/order-lines.actions';
import { OrderLinesService } from '../order-lines.service';
import { OrdersActions } from '../store/orders.actions';
import { map, tap } from 'rxjs/operators';
import { OrdersSelectors } from '../store/orders.selector';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent implements OnInit {

  order$: Observable<Order> = this.store.select(OrdersSelectors.selectOrder(this.route.snapshot.params.orderId));
  orderLines$: Observable<OrderLine[]> = this.store.select(OrderLinesSelectors.selectAllOrderLines);


  constructor(
    private route: ActivatedRoute,
    private store: Store<AppState>,
    private service: OrdersService,
    private orderLineService: OrderLinesService,
  ) { }

  ngOnInit() {
    this.store.dispatch(OrdersActions.orderRequested({ id: this.orderId }));
    this.store.dispatch(OrderLinesActions.linesRequested({ id: this.orderId }));
  }

  releaseOrder() {
    this.service.updateOrderStatus(this.orderId, OrderStatus.RELEASED)
      .subscribe(
        ({ id }) => this.store.dispatch(OrdersActions.orderReleased({ id }))
      );
  }

  removeOrderLine({ id }: OrderLine) {
    this.orderLineService.removeOrderLine(this.orderId, id)
    .subscribe(
      () => this.store.dispatch(OrderLinesActions.orderLineRemoved({ id }))
    );
  }

  get orderId() {
    return this.route.snapshot.params.orderId;
  }

}
