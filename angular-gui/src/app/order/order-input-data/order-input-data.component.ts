import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { OrdersService } from '../orders.service';
import { OrdersActions } from '../store/orders.actions';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-order-input-data',
  templateUrl: './order-input-data.component.html',
  styleUrls: ['./order-input-data.component.scss']
})
export class OrderInputDataComponent implements OnInit {

  form: FormGroup;
  error?: string;

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
    this.form.disable();
    this.error = undefined;
    this.ordersService.createOrder(this.form.value.name)
    .pipe(
      finalize(() => this.form.enable()),
    )
    .subscribe(
      order => this.store.dispatch(OrdersActions.orderCreated({ order })),
      error => this.error = 'Error: ' + error.error.message,
    );
  }

}
