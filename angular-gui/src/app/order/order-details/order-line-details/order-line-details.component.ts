import { Component, OnInit, Input } from '@angular/core';
import { OrderLine } from '../../store/orders.model';
import { OrderLinesService } from '../../order-lines.service';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/app.reducer';
import { OrderLinesActions } from '../../store/order-lines.actions';

@Component({
  selector: 'app-order-line-details',
  templateUrl: './order-line-details.component.html',
  styleUrls: ['./order-line-details.component.scss']
})
export class OrderLineDetailsComponent implements OnInit {
  
  @Input()
  orderLine: OrderLine;

  constructor(
    private readonly service: OrderLinesService,
    private readonly store: Store<AppState>,
  ) { }

  ngOnInit() {
  }

  remove() {
    this.service.removeOrderLine(1, this.orderLine.id).subscribe(
      () => this.store.dispatch(OrderLinesActions.orderLineRemoved({ id: this.orderLine.id }))
    );
  }

}
