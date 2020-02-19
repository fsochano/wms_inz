import { AppUsersSelectors } from './store/users.seletors';
import { AppUsersActions } from './store/users.actions';
import { UsersService } from './users.service';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { AddAuthorityDialogComponent } from './add-authority-dialog/add-authority-dialog.component';
import { ColumnSchema } from 'src/app/shared/table/column-schema.model';
import { Component, OnInit } from '@angular/core';
import { AppUser, AppUserParams } from './app-user.model';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordDialogComponent } from './change-password-dialog/change-password-dialog.component';
import { Store } from '@ngrx/store';
import { filter, switchMap, map } from 'rxjs/operators';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  readonly columnSchema: ColumnSchema<AppUser>[] = [{
    key: 'username',
    header: 'Username',
  }];
  readonly displayedColumns = [...this.columnSchema.map(s => s.key), 'authorities', 'bt-actions'];

  users$ = this.store.select(AppUsersSelectors.selectAllUsers);

  constructor(
    private readonly service: UsersService,
    private readonly dialog: MatDialog,
    private readonly store: Store<{}>,
    ) { }

  ngOnInit() {
    this.store.dispatch(AppUsersActions.loadUsers());
  }

  changeUserPassword(user: AppUser) {
    this.dialog.open(ChangePasswordDialogComponent, {
      width: '250px',
      data: {
        user,
      },
    })
    .afterClosed()
    .pipe(
      filter(p => !!p),
      switchMap(newPassword => this.service.changePassword(user, newPassword)),
    )
    .subscribe(usr => alert(`User ${usr.username} password changed.`));
  }

  addAuthority(user: AppUser) {
    this.dialog.open(AddAuthorityDialogComponent, {
      width: '250px',
      data: {
        user,
      },
    })
    .afterClosed()
    .subscribe(authority => {
      if (authority) {
        this.service.addAuthority(user, authority).subscribe(
          usr => this.store.dispatch(AppUsersActions.userAuthorityAdded({ user: usr, authority }))
        );
      }
    });
  }

  removeAuthority(user: AppUser, authority: string) {
    this.dialog.open(ConfirmDialogComponent, {
      width: '250px',
      data: {
        message: `Are sure you want to remove ${authority} authority from ${user.username} user?`,
      },
    })
    .afterClosed()
    .pipe(
      filter(c => !!c),
      switchMap(() => this.service.removeAuthority(user, authority)),
      map(usr => AppUsersActions.userAuthorityRemoved({ user: usr, authority })),
    )
    .subscribe(action => this.store.dispatch(action));
  }

  removeUser(user: AppUser) {
    this.dialog.open(ConfirmDialogComponent, {
      width: '250px',
      data: {
        message: `Are sure you want to remove ${user.username} user?`,
      }
    })
    .afterClosed()
    .pipe(
      filter(c => !!c),
      switchMap(() => this.service.removeUser(user)),
      map(() => AppUsersActions.userRemoved({ user })),
    )
    .subscribe(action => this.store.dispatch(action));
  }

}
