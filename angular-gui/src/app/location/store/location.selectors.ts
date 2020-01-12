import { LocationsAdapter, LocationsState } from './location.reducer';
import { createFeatureSelector, createSelector } from '@ngrx/store';

const {
    selectAll,
} = LocationsAdapter.getSelectors();

class Selectors {
    feature = createFeatureSelector<LocationsState>('locationsFeature');

    selectAllLocations = createSelector(
        this.feature,
        selectAll
    );

}

export const LocationsSelectors = new Selectors();
