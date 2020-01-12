import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';
import { Order } from '../../store/orders.model';


@Component({
  selector: 'app-order-properties',
  templateUrl: './order-properties.component.html',
  styleUrls: ['./order-properties.component.scss'],
})
export class OrderPropertiesComponent implements OnInit {

  @Input()
  order: Order

  constructor() { }

  ngOnInit() {
  }

}
