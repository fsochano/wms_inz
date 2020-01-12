import { StoreModule } from '@ngrx/store';
import { NgModule } from '@angular/core';
import { LocationsReducer } from './location.reducer';

@NgModule({
    declarations: [],
    imports: [
      StoreModule.forFeature('locationsFeature', LocationsReducer),
    ]
  })
  export class LocationStoreModule { }