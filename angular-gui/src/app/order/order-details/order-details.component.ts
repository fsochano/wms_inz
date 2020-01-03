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

  order$: Observable<Order>; 
  orderLines$: Observable<OrderLine[]> = this.store.select(OrderLinesSelectors.selectAllOrderLines);

  constructor(
    private route: ActivatedRoute,
    private store: Store<AppState>,
    private service: OrdersService,
  ) { }

  ngOnInit() {
    this.order$ = this.store.select(OrdersSelectors.selectOrder(this.route.snapshot.params.orderId))
    this.store.dispatch(OrderLinesActions.linesRequested({ id: this.route.snapshot.params.orderId }));
  }

  releaseOrder() {
    this.service.updateOrderStatus(this.route.snapshot.params.orderId, OrderStatus.RELEASED).subscribe(
      order => {
        this.store.dispatch(OrdersActions.orderReleased({ id: order.id }));
      }
    );
  }

}
