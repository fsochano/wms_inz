import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { OrdersService } from '../orders.service';
import { OrdersActions } from '../store/orders.actions';

@Component({
  selector: 'app-order-input-data',
  templateUrl: './order-input-data.component.html',
  styleUrls: ['./order-input-data.component.scss']
})
export class OrderInputDataComponent implements OnInit {

  form: FormGroup;

  constructor(
    fb: FormBuilder,
    private ordersService: OrdersService,
    private store: Store<{}>) {
    this.form = fb.group({
      name: [null, [Validators.required]],
    });
  }

  ngOnInit() {
  }

  createOrder() {
    const val = this.form.value;
    this.ordersService.createOrder(val.name)
    .subscribe(
      order => this.store.dispatch(OrdersActions.orderCreated({ order })),
      () => alert('order not created'),
    );
  }

}
