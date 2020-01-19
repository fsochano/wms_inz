import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShippingComponent } from '../shipping/shipping.component';
import { AuthGuard } from '../auth/auth.guard';



@NgModule({
  declarations: [ShippingComponent],
  imports: [
    CommonModule,
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
