import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContainerInputComponent } from './container-input/container-input.component';
import { SharedModule } from '../shared/shared.module';
import { StoreModule } from '@ngrx/store';
import { ContainersReducer } from './store/containers.reducer';



@NgModule({
  declarations: [
    ContainerInputComponent,
  ],
  exports: [
    ContainerInputComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    StoreModule.forFeature('containersFeature', ContainersReducer),
  ]
})
export class ContainerModule { }
