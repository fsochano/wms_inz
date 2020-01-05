import { AppMaterialModule } from './../app-material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SkuComponent } from './sku.component';
import { RouterModule } from '@angular/router';
import { AuthGuard } from '../auth/auth.guard';
import { SkusReducers } from './store/sku.reducer';
import { StoreModule } from '@ngrx/store';
import { SkuInputDataComponent } from './sku-input-data/sku-input-data.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [SkuComponent, SkuInputDataComponent],
  imports: [
    CommonModule,
    AppMaterialModule,
    SharedModule,
    ReactiveFormsModule,
    RouterModule.forChild([
      {
        path: 'sku',
        component: SkuComponent,
        canActivate: [AuthGuard],
      },
    ]),
    StoreModule.forFeature('skuFeature', SkusReducers),
  ]
})
export class SkuModule { }
