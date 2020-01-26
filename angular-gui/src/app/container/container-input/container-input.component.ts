import { Sku } from './../../sku/store/sku.model';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { SkuService } from '../../sku/sku.service';
import { Store } from '@ngrx/store';
import { ContainersActions } from '../store/containers.actions';
import { ContainerService } from '../container.service';

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


  constructor(
    fb: FormBuilder,
    private containerService: ContainerService,
    skuService: SkuService,
    private store: Store<{}>
    ) {
    this.form = fb.group({
      containerSize: [null, [Validators.required, Validators.min(1)]],
      skuId: [null, [Validators.required]],
      skuQty: [null, [Validators.required, Validators.min(0)]],
      skuCapacity: [null, [Validators.required, Validators.min(1)]]
    });
    this.sku$ = skuService.loadSkus();
  }

  ngOnInit() {
  }

  createContainer() {
    this.error = undefined;
    const { containerSize, skuId, skuQty, skuCapacity } = this.form.value;
    this.containerService.createContainer({ locationId: this.locationId, containerSize, skuId, skuQty, skuCapacity })
    .subscribe(
      container => this.store.dispatch(ContainersActions.containerCreated({ container })),
      error => this.error = error.error.message
    );
  }


}
