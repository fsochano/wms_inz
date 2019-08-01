import { OrderLine } from './../../store/orders.model';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/app.reducer';
import { OrderLinesActions } from '../../store/order-lines.actions';
import { OrderLinesService } from '../../order-lines.service';

@Component({
  selector: 'app-order-input-details',
  templateUrl: './order-input-details.component.html',
  styleUrls: ['./order-input-details.component.scss']
})
export class OrderInputDetailsComponent implements OnInit {

  @Input() orderId: number;

  form: FormGroup;

  constructor(
    fb: FormBuilder,
    private orderLinesService: OrderLinesService,
    private router: Router,
    private store: Store<AppState>) {
    this.form = fb.group({
      qty: [1, [Validators.required]],
      item: ['item', [Validators.required]],
    });
  }

  ngOnInit() {
  }

  createOrderLine() {
    const val = this.form.value;
    this.orderLinesService.createOrderLine(this.orderId,
      { qty:  val.qty, item: val.item })
    .subscribe(
      line => this.store.dispatch(OrderLinesActions.orderLineCreated({ line })),
      () => alert('order line not created'),
    );
  }

}
