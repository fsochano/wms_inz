import { EntityState, EntityAdapter, createEntityAdapter, Update } from '@ngrx/entity';
import { createReducer, on } from '@ngrx/store';
import { AppUser } from '../app-user.model';
import { AppUsersActions } from './users.actions';

export interface AppUsersState extends EntityState<AppUser> {
}

export const AppUsersAdapter: EntityAdapter<AppUser> = createEntityAdapter<AppUser>({
  selectId: user => user.username,
});

const initialState: AppUsersState = AppUsersAdapter.getInitialState({});

export const AppUsersReducers = createReducer(
  initialState,
  on(AppUsersActions.loadUsers, (state) => AppUsersAdapter.removeAll(state)),
  on(AppUsersActions.usersLoaded, (state, { users }) => AppUsersAdapter.addAll(users, state)),
  on(AppUsersActions.userCreated, (state, { user }) => AppUsersAdapter.addOne(user, state)),
  on(AppUsersActions.userRemoved, (state, { user }) => AppUsersAdapter.removeOne(user.username, state)),
  on(AppUsersActions.userAuthorityAdded, (state, { user, authority }) => {
    const newAuthorities = new Set(user.authorities).add(authority);
    const updateEntry: Update<AppUser> = {
      id: user.username,
      changes: { authorities: Array.from(newAuthorities) },
    };
    return AppUsersAdapter.updateOne(updateEntry, state);
  }),
  on(AppUsersActions.userAuthorityRemoved, (state, { user, authority }) => {
    const updateEntry: Update<AppUser> = {
      id: user.username,
      changes: { authorities: user.authorities.filter(a => a !== authority) },
    };
    return AppUsersAdapter.updateOne(updateEntry, state);
  }),
);
