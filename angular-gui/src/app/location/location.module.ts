import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { LocationStoreModule } from './store/location-store.module';
import { RouterModule } from '@angular/router';
import { LocationComponent } from './location.component';
import { LocationDetailsComponent } from './location-details/location-details.component';
import { ContainerModule } from '../container/container.module';
import { LocationInputComponent } from './location-input/location-input.component';

@NgModule({
  declarations: [
    LocationComponent,
    LocationDetailsComponent,
    LocationInputComponent,
  ],
  exports: [
    LocationComponent,
    LocationDetailsComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    ContainerModule,
    LocationStoreModule,
    RouterModule,
  ]
})
export class LocationModule { }
