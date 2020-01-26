import { createFeatureSelector, createSelector } from '@ngrx/store';
import { OrdersState, OrdersAdapter } from './orders.reducer';

const {
    selectAll,
    selectEntities,
} = OrdersAdapter.getSelectors();

class Selectors {
    feature = createFeatureSelector<OrdersState>('orderFeature');

    selectAllOrders = createSelector(
        this.feature,
        selectAll
    );

    selectOrdersMap = createSelector(
        this.feature,
        selectEntities
    );

    isLoading = createSelector(
        this.feature,
        state => state.isLoading,
    );

    selectOrder(id: string) {
        return createSelector(
            this.selectOrdersMap,
            map => map[id]
        );
    }
}

export const OrdersSelectors = new Selectors();
