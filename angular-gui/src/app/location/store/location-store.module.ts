import { StoreModule } from '@ngrx/store';
import { NgModule } from '@angular/core';
import { LocationsReducer } from './location.reducer';
import { EffectsModule } from '@ngrx/effects';
import { LocationEffects } from './location.effects';

@NgModule({
    declarations: [],
    imports: [
      StoreModule.forFeature('locationsFeature', LocationsReducer),
      EffectsModule.forFeature([LocationEffects]),
    ]
  })
  export class LocationStoreModule { }