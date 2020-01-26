import { SkusActions } from './../store/sku.actions';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SkuService } from '../sku.service';
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-sku-input-data',
  templateUrl: './sku-input-data.component.html',
  styleUrls: ['./sku-input-data.component.scss']
})
export class SkuInputDataComponent implements OnInit {
  form: FormGroup;

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
    const val = this.form.value;
    this.service.createSku(val.name, val.description)
    .subscribe(
      sku => this.store.dispatch(SkusActions.skuCreated({ sku })),
      () => alert('Sku not created'),
    );
  }

}
