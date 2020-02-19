import { ContainerType } from './../store/containers.model';
import { Sku } from './../../sku/store/sku.model';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { SkuService } from '../../sku/sku.service';
import { Store } from '@ngrx/store';
import { ContainersActions } from '../store/containers.actions';
import { ContainerService } from '../container.service';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-container-input',
  templateUrl: './container-input.component.html',
  styleUrls: ['./container-input.component.scss']
})
export class ContainerInputComponent implements OnInit {

  @Input() locationId: number;

  form: FormGroup;

  error?: string;
  sku$: Observable<Sku[]>;
  containerTypes: ContainerType[] = ['STORAGE', 'SHIPPING'];


  constructor(
    fb: FormBuilder,
    private containerService: ContainerService,
    skuService: SkuService,
    private store: Store<{}>
  ) {
    this.form = fb.group({
      type: fb.control(null, [Validators.required]),
      containerSize: fb.control(null, [Validators.required, Validators.min(1)]),
      skuId: fb.control(null, [Validators.required]),
      skuQty: fb.control(null, [Validators.required, Validators.min(0)]),
      skuCapacity: fb.control(null, [Validators.required, Validators.min(1)]),
    });
    this.sku$ = skuService.loadSkus();
  }

  ngOnInit() {
  }

  createContainer() {
    this.form.disable();
    this.error = undefined;
    const { type, containerSize, skuId, skuQty, skuCapacity } = this.form.value;
    this.containerService.createContainer({ type, locationId: this.locationId, containerSize, skuId, skuQty, skuCapacity })
      .pipe(
        finalize(() => this.form.enable()),
      ).subscribe(
        container => this.store.dispatch(ContainersActions.containerCreated({ container })),
        error => this.error = 'Error: ' + error.error.message
      );
  }


}
