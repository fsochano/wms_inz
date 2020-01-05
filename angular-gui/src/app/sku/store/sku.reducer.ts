import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';

import { Sku } from './sku.model';
import { createReducer, on } from '@ngrx/store';
import { SkusActions } from './sku.actions';

export interface SkuState extends EntityState<Sku> {
    allSkusLoaded: boolean;
 }
 
export const SkusAdapter: EntityAdapter<Sku> = createEntityAdapter<Sku>();

const initialState: SkuState = SkusAdapter.getInitialState({
    allSkusLoaded: false,
});

export const SkusReducers = createReducer(
    initialState,
    on(SkusActions.skusLoaded, (state, { skus }) => SkusAdapter.addAll(skus, state)),
    on(SkusActions.skuCreated, (state, { sku }) => SkusAdapter.addOne(sku, state)),
);