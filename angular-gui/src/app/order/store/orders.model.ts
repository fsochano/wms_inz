import { Sku } from '../../sku/store/sku.model';
import { Auditable } from '../../shared/entity.model';

export enum OrderStatus {
    HOLD = 'HOLD',
    RELEASED = 'RELEASED',
    COMPLETED = 'COMPLETED',
    SHIPPED = 'SHIPPED',
}

export interface Order extends Auditable {
    id: number;
    name: string;
    status: OrderStatus;
}

export interface OrderLine extends Auditable {
    id: number;
    qty: number;
    allocated: number;
    sku: Sku;
}
