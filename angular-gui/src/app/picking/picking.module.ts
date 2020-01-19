import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PickingComponent } from '../picking/picking.component';
import { RouterModule } from '@angular/router';
import { AuthGuard } from '../auth/auth.guard';
import { SharedModule } from '../shared/shared.module';
import { PickTaskComponent } from './pick-task/pick-task.component';



@NgModule({
  declarations: [PickingComponent, PickTaskComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild([
      {
        path: 'picking',
        component: PickingComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'picking/:pickTaskId',
        component: PickTaskComponent,
        canActivate: [AuthGuard],
      }
    ]),
  ]
})
export class PickingModule { }
