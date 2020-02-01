import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EffectsModule } from '@ngrx/effects';
import { RouterModule } from '@angular/router';
import { StoreModule } from '@ngrx/store';
import { LoginComponent } from './login/login.component';
import { authReducer } from './auth.readucer';
import { SharedModule } from './../shared/shared.module';
import { AuthEffects } from './auth.effects';
import { LoginGuard } from './login.guard';

@NgModule({
  declarations: [LoginComponent],
  exports: [LoginComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild([{
      path: 'login',
      component: LoginComponent,
      canActivate: [LoginGuard],
    }]),
    StoreModule.forFeature('authFeature', authReducer),
    EffectsModule.forFeature([AuthEffects]),
  ]
})
export class AuthModule {
}
