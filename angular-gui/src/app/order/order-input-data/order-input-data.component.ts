import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/app.reducer';
import { OrdersService } from '../orders.service';
import { tap } from 'rxjs/operators';
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
    private router: Router,
    private store: Store<AppState>) {
    this.form = fb.group({
      id: ['1', [Validators.required]],
      name: ['name', [Validators.required]],
    });
  }
  
  ngOnInit() {

  }

  createOrder(){
    const val = this.form.value;
    this.ordersService.createOrder(val.id,val.name)
    .subscribe(
      order => this.store.dispatch(OrdersActions.orderCreated({ order })),
      () => alert('order not created'),
    )
  }

}
