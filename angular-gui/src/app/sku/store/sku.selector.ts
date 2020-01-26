import { SkusAdapter, SkuState } from "./sku.reducer";
import { createFeatureSelector, createSelector } from '@ngrx/store';

const {
    selectAll,
    // selectEntities,
} = SkusAdapter.getSelectors();

class Selectors {
    feature = createFeatureSelector<SkuState>('skuFeature');

    selectAllSkus = createSelector(
        this.feature,
        selectAll
    );
}
export const SkusSelectors = new Selectors();