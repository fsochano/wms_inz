import { Sku } from './../../sku/store/sku.model';

export interface Container {
    id: number;
    containerSize: number;
    skuQty: number;
    skuCapacity: number;
    sku: Sku;
}