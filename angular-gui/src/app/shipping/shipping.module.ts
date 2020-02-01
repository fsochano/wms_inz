import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShippingComponent } from '../shipping/shipping.component';
import { AuthGuard } from '../auth/auth.guard';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [ShippingComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild([
      {
        path: 'shipping',
        component: ShippingComponent,
        canActivate: [AuthGuard],
      },
    ]),
  ]
})
export class ShippingModule { }
