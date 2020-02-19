import { SkusActions } from './../store/sku.actions';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SkuService } from '../sku.service';
import { Store } from '@ngrx/store';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-sku-input-data',
  templateUrl: './sku-input-data.component.html',
  styleUrls: ['./sku-input-data.component.scss']
})
export class SkuInputDataComponent implements OnInit {
  form: FormGroup;
  error?: string;

  constructor(
    fb: FormBuilder,
    private service: SkuService,
    private store: Store<{}>) {
    this.form = fb.group({
      name: [null, [Validators.required]],
      description: [null, [Validators.required]],
    });
  }


  ngOnInit() {
  }

  createSku() {
    this.form.disable();
    this.error = undefined;
    const val = this.form.value;
    this.service.createSku(val.name, val.description)
      .pipe(
        finalize(() => this.form.enable()),
      )
      .subscribe(
        sku => this.store.dispatch(SkusActions.skuCreated({ sku })),
        error => this.error = 'Error: ' + error.error.message,
      );
  }

}
