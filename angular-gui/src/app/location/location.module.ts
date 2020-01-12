import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { LocationStoreModule } from './store/location-store.module';
import { RouterModule } from '@angular/router';
import { AuthGuard } from '../auth/auth.guard';
import { LocationComponent } from './location.component';

@NgModule({
  declarations: [
    LocationComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild([
      {
        path: 'location',
        component: LocationComponent,
        canActivate: [AuthGuard],
      },
    ]),
    LocationStoreModule,
  ]
})
export class LocationModule { }
