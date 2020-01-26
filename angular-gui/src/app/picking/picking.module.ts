import { PickListsEffects } from './store/pick-lists.effects';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PickingComponent } from '../picking/picking.component';
import { RouterModule } from '@angular/router';
import { AuthGuard } from '../auth/auth.guard';
import { SharedModule } from '../shared/shared.module';
import { PickTaskComponent } from './pick-task/pick-task.component';
import { StoreModule } from '@ngrx/store';
import { PickListsReducers } from './store/pick-lists.reducer';
import { PickListsFeatureName } from './store/pick-lists.selector';
import { PickTaskModule } from './pick-task/pick-task.module';
import { EffectsModule } from '@ngrx/effects';

@NgModule({
  declarations: [PickingComponent],
  imports: [
    PickTaskModule,
    CommonModule,
    SharedModule,
    RouterModule.forChild([
      {
        path: 'picking',
        component: PickingComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'picking/:pickListId',
        component: PickTaskComponent,
        canActivate: [AuthGuard],
      }
    ]),
    StoreModule.forFeature(PickListsFeatureName, PickListsReducers),
    EffectsModule.forFeature([PickListsEffects]),
  ]
})
export class PickingModule { }
