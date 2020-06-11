import { ShipmentComponent } from './shipment.component';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthGuard } from '../auth/auth.guard';
import { SharedModule } from '../shared/shared.module';
import { StoreModule } from '@ngrx/store';
import { ShipmentFeatureName } from './store/shipment.selector';
import { ShipmentReducers } from './store/shipment.reducer';
import { EffectsModule } from '@ngrx/effects';
import { ShipmentEffects } from './store/shipment.effects';



@NgModule({
  declarations: [ShipmentComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild([
      {
        path: 'shipment',
        component: ShipmentComponent,
        canActivate: [AuthGuard],
      },
    ]),
    StoreModule.forFeature(ShipmentFeatureName, ShipmentReducers),
    EffectsModule.forFeature([ShipmentEffects]),
  ]
})
export class ShipmentModule { }
