import { EffectsModule } from '@ngrx/effects';
import { SharedModule } from 'src/app/shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from '../users/users.component';
import { AddAuthorityDialogComponent } from './add-authority-dialog/add-authority-dialog.component';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { UserCreationFormComponent } from './user-creation-form/user-creation-form.component';
import { ChangePasswordDialogComponent } from './change-password-dialog/change-password-dialog.component';
import { UsersStoreModule } from './store/users-store.module';

@NgModule({
  declarations: [
    UsersComponent,
    ChangePasswordDialogComponent,
    AddAuthorityDialogComponent,
    ConfirmDialogComponent,
    UserCreationFormComponent,
  ],
  exports: [UsersComponent],
  imports: [
    CommonModule,
    SharedModule,
    UsersStoreModule,
  ],
})
export class UsersModule { }
