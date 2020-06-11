import { createFeatureSelector, createSelector } from '@ngrx/store';
import { ShipmentAdapter, ShipmentState } from './shipment.reducer';

export const ShipmentFeatureName = 'shipmentFeature';

const {
    selectAll,
    // selectEntities,
} = ShipmentAdapter.getSelectors();

class Selectors {
    feature = createFeatureSelector<ShipmentState>(ShipmentFeatureName);

    selectAllShipment = createSelector(
        this.feature,
        selectAll
    );
}
export const ShipmentSelectors = new Selectors();
