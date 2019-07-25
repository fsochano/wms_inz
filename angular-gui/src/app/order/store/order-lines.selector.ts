import { OrderLinesAdapter, OrderLinesState } from './order-lines.reducer';
import { createFeatureSelector, createSelector } from '@ngrx/store';

const {
    selectAll,
} = OrderLinesAdapter.getSelectors();

class Selectors {
    feature = createFeatureSelector<OrderLinesState>('orderLinesFeature');

    selectAllOrderLines = createSelector(
        this.feature,
        selectAll
    );

}

export const OrderLinesSelectors = new Selectors();
