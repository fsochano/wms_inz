import { Component, Input, Output, EventEmitter } from '@angular/core';
import { OrderLine } from '../../store/orders.model';
import { OrderLinesService } from '../../order-lines.service';
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-order-line-details',
  templateUrl: './order-line-details.component.html',
  styleUrls: ['./order-line-details.component.scss']
})
export class OrderLineDetailsComponent {

  @Input()
  orderLine!: OrderLine;

  @Output()
  remove = new EventEmitter<OrderLine>();

  constructor(
    private readonly service: OrderLinesService,
    private readonly store: Store<{}>,
  ) { }

}
