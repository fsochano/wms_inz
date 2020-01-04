import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderComponent } from './orders.component';
import { StoreModule } from '@ngrx/store';
import { OrdersReducers } from './store/orders.reducer';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { EffectsModule } from '@ngrx/effects';
import { OrdersEffects } from './store/orders.effects';
import { RouterModule } from '@angular/router';
import { AuthGuard } from '../auth/auth.guard';
import { OrderInputDataComponent } from './order-input-data/order-input-data.component';
import { ReactiveFormsModule } from '@angular/forms';
import { OrderLinesReducers } from './store/order-lines.reducer';
import { OrderInputDetailsComponent } from './order-details/order-input-details/order-input-details.component';
import { OrderLinesEffects } from './store/order-lines.effects';
import { AppMaterialModule } from '../app-material.module';
import { OrderLineDetailsComponent } from './order-details/order-line-details/order-line-details.component';

@NgModule({
  declarations: [
    OrderComponent,
    OrderDetailsComponent,
    OrderInputDataComponent,
    OrderInputDetailsComponent,
    OrderLineDetailsComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AppMaterialModule,
    RouterModule.forChild([
      {
        path: 'order',
        component: OrderComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'order/:orderId',
        component: OrderDetailsComponent,
        canActivate: [AuthGuard],
      },
    ]),
    StoreModule.forFeature('orderFeature', OrdersReducers),
    StoreModule.forFeature('orderLinesFeature', OrderLinesReducers),
    EffectsModule.forFeature([OrdersEffects, OrderLinesEffects]),
  ]
})
export class OrderModule { }
