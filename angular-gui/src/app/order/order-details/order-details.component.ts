import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState } from '../../app.reducer';
import { Order, OrderLine } from '../store/orders.model';
import { Observable } from 'rxjs';
import { OrderLinesSelectors } from '../store/order-lines.selector';
import { OrdersService } from '../orders.service';
import { OrderLinesActions } from '../store/order-lines.actions';
import { OrderLinesService } from '../order-lines.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent implements OnInit {

  order: Order;
  orderLines$: Observable<OrderLine[]>;

  constructor(
    private route: ActivatedRoute,
    private service: OrderLinesService,
    private router: Router,
    private store: Store<AppState>,
  ) { }

  ngOnInit() {
    this.order = this.route.snapshot.data.order;
    this.orderLines$ = this.store.select(OrderLinesSelectors.selectAllOrderLines);
    this.store.dispatch(OrderLinesActions.linesRequested({ id: this.order.id }));
  }

}
