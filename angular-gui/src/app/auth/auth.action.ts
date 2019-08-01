import { User } from './auth.model';
import { createAction, props } from '@ngrx/store';

export const AuthActions = {
    login: createAction('[Login Component] Logged in', props<{ user: User }>()),
    logout: createAction('[Login Component] Logout'),
};
