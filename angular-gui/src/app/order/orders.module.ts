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
import { OrderResolver } from './orders.resolver';
import { OrderInputDataComponent } from './order-input-data/order-input-data.component';
import { MatCardModule, MatInputModule, MatButtonModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { OrderLinesReducers } from './store/order-lines.reducer';



@NgModule({
  declarations: [OrderComponent, OrderDetailsComponent, OrderInputDataComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
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
        resolve: {
          order: OrderResolver,
        },
      },
    ]),
    StoreModule.forFeature('orderFeature', OrdersReducers),
    StoreModule.forFeature('orderLinesFeature', OrderLinesReducers),
    EffectsModule.forFeature([OrdersEffects]),
  ]
})
export class OrderModule { }
