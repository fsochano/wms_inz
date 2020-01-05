import { Sku } from './sku.model';
import { createAction, props } from "@ngrx/store";

class Actions{

    readonly skusLoaded = createAction(
        '[Sku component] Skus Loaded',
        props<{ skus: Sku[] }>(),
    );

    readonly skuCreated = createAction(
        '[Sku component] Sku Created',
        props<{ sku: Sku }>(),
    );
}

export const SkusActions = new Actions();