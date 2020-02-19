import { createAction, props } from '@ngrx/store';
import { AppUser } from '../app-user.model';

class Actions {
  readonly loadUsers = createAction(
      '[Users component] Load users',
  );

  readonly usersLoaded = createAction(
      '[Users component] Users Loaded',
      props<{ users: AppUser[] }>(),
  );

  readonly userCreated = createAction(
      '[Users component] User Created',
      props<{ user: AppUser }>(),
  );

  readonly userRemoved = createAction(
      '[Users component] User Removed',
      props<{ user: AppUser }>(),
  );

  readonly userAuthorityAdded = createAction(
      '[Users component] User Authority Added',
      props<{ user: AppUser, authority: string }>(),
  );

  readonly userAuthorityRemoved = createAction(
      '[Users component] User Authority Removed',
      props<{ user: AppUser, authority: string }>(),
  );
}

export const AppUsersActions = new Actions();
