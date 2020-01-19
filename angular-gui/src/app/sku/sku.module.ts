import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SkuComponent } from './sku.component';
import { RouterModule } from '@angular/router';
import { SkusReducers } from './store/sku.reducer';
import { StoreModule } from '@ngrx/store';
import { SkuInputDataComponent } from './sku-input-data/sku-input-data.component';
import { SharedModule } from '../shared/shared.module';
import { SkuDetailsComponent } from './sku-details/sku-details.component';



@NgModule({
  declarations: [SkuComponent, SkuInputDataComponent, SkuDetailsComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    StoreModule.forFeature('skuFeature', SkusReducers),
  ]
})
export class SkuModule { }
