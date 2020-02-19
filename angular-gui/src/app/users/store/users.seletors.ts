import { AppUsersState, AppUsersAdapter } from './users.reducer';
import { createFeatureSelector, createSelector } from '@ngrx/store';

const {
    selectAll,
    // selectEntities,
} = AppUsersAdapter.getSelectors();

class Selectors {
    feature = createFeatureSelector<AppUsersState>('usersFeature');

    selectAllUsers = createSelector(
        this.feature,
        selectAll,
    );
}
export const AppUsersSelectors = new Selectors();
