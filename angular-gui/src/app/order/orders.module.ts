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
import { OrderLineDetailsComponent } from './order-details/order-line-details/order-line-details.component';
import { SharedModule } from '../shared/shared.module';
import { OrderPropertiesComponent } from './order-details/order-properties/order-properties.component';
import { OrderingGuard } from './ordering.guard';

@NgModule({
  declarations: [
    OrderComponent,
    OrderDetailsComponent,
    OrderInputDataComponent,
    OrderInputDetailsComponent,
    OrderLineDetailsComponent,
    OrderPropertiesComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild([
      {
        path: 'order',
        component: OrderComponent,
        canActivate: [AuthGuard, OrderingGuard],
      },
      {
        path: 'order/:orderId',
        component: OrderDetailsComponent,
        canActivate: [AuthGuard, OrderingGuard],
      },
    ]),
    StoreModule.forFeature('orderFeature', OrdersReducers),
    StoreModule.forFeature('orderLinesFeature', OrderLinesReducers),
    EffectsModule.forFeature([OrdersEffects, OrderLinesEffects]),
  ]
})
export class OrderModule { }
