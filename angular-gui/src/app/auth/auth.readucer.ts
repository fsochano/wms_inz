import { AuthActions } from './auth.action';
import { createReducer, on } from '@ngrx/store';
import { User } from './auth.model';

export interface AuthState {
    user?: User;
    token?: string;
}

const initialState: AuthState = {};

export const authReducer = createReducer(
    initialState,
    on(AuthActions.login, (state, { user }) => ({ ...state, user })),
    on(AuthActions.logout, state => ({ ...state, user: undefined })),
);
