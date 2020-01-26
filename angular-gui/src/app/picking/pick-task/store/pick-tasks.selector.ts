import { createFeatureSelector, createSelector } from '@ngrx/store';
import { PickTasksAdapter, PickTasksState } from './pick-tasks.reducer';

export const PickTasksFeatureName = 'pickTaskFeature';

const {
    selectAll,
    // selectEntities,
} = PickTasksAdapter.getSelectors();

class Selectors {
    feature = createFeatureSelector<PickTasksState>(PickTasksFeatureName);

    selectAllPickTasks = createSelector(
        this.feature,
        selectAll
    );
}
export const PickTasksSelectors = new Selectors();