import { Auditable } from './../../shared/entity.model';
import { Sku } from './../../sku/store/sku.model';

export type ContainerType = 'SHIPPING' | 'STORAGE';

export interface Container extends Auditable {
    id: number;
    type: ContainerType;
    containerSize: number;
    skuCapacity: number;
    sku: Sku;
    skuQty: number;
    allocatedQty: number;
    freeQty: number;
}
