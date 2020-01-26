import { PickListsAdapter, PickListsState } from './pick-lists.reducer';
import { createFeatureSelector, createSelector } from '@ngrx/store';

export const PickListsFeatureName = 'pickListFeature';

const {
    selectAll,
    // selectEntities,
} = PickListsAdapter.getSelectors();

class Selectors {
    feature = createFeatureSelector<PickListsState>(PickListsFeatureName);

    selectAllPickLists = createSelector(
        this.feature,
        selectAll
    );
}
export const PickListsSelectors = new Selectors();