import { SkuService } from './../../../sku/sku.service';
import { Sku } from './../../../sku/store/sku.model';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { OrderLinesActions } from '../../store/order-lines.actions';
import { OrderLinesService } from '../../order-lines.service';
import { Order } from '../../store/orders.model';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';


@Component({
  selector: 'app-order-input-details',
  templateUrl: './order-input-details.component.html',
  styleUrls: ['./order-input-details.component.scss']
})
export class OrderInputDetailsComponent implements OnInit {

  @Input() order: Order;

  form: FormGroup;

  error?: string;
  sku$: Observable<Sku[]>;


  constructor(
    fb: FormBuilder,
    private orderLinesService: OrderLinesService,
    skuService: SkuService,
    private store: Store<{}>
  ) {
    this.form = fb.group({
      qty: [null, [Validators.required, Validators.min(1)]],
      skuId: [null, [Validators.required]],
    });
    this.sku$ = skuService.loadSkus();
  }

  ngOnInit() {
  }

  createOrderLine() {
    this.form.disable();
    this.error = undefined;
    const val = this.form.value;
    this.orderLinesService.createOrderLine(this.order.id,
      { qty: val.qty, skuId: val.skuId })
      .pipe(
        finalize(() => this.form.enable()),
      )
      .subscribe(
        line => this.store.dispatch(OrderLinesActions.orderLineCreated({ line })),
        error => this.error = 'Error: ' + error.error.message,
      );
  }

}
