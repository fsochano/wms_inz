import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { StoreModule } from '@ngrx/store';
import { PickTasksFeatureName } from './store/pick-tasks.selector';
import { PickTasksReducers } from './store/pick-tasks.reducer';
import { PickTaskComponent } from './pick-task.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EffectsModule } from '@ngrx/effects';
import { PickTasksEffects } from './store/pick-tasks.effects';

@NgModule({
    declarations: [PickTaskComponent],
    exports: [PickTaskComponent],
    imports: [
      CommonModule,
      SharedModule,
      RouterModule,
      StoreModule.forFeature(PickTasksFeatureName, PickTasksReducers),
      EffectsModule.forFeature([PickTasksEffects]),
    ]
  })
  export class PickTaskModule { }