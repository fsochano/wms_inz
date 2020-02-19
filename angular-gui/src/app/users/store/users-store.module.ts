import { NgModule } from '@angular/core';
import { StoreModule } from '@ngrx/store';
import { AppUsersReducers } from './users.reducer';
import { EffectsModule } from '@ngrx/effects';
import { UsersEffects } from './users.effects';

@NgModule({
  imports: [
    StoreModule.forFeature('usersFeature', AppUsersReducers),
    EffectsModule.forFeature([UsersEffects])
  ],
})
export class UsersStoreModule { }
