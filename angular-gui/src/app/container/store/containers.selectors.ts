import { createFeatureSelector, createSelector } from '@ngrx/store';
import { ContainersAdapter, ContainersState } from './containers.reducer';

const {
    selectAll,
} = ContainersAdapter.getSelectors();

class Selectors {
    feature = createFeatureSelector<ContainersState>('containersFeature');

    selectAllContainers = createSelector(
        this.feature,
        selectAll
    );

}

export const ContainersSelectors = new Selectors();