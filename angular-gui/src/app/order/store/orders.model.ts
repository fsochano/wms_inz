import { Sku } from '../../sku/store/sku.model';

export enum OrderStatus {
    HOLD = 'HOLD',
    RELEASED = 'RELEASED',
    COMPLETED = 'COMPLETED',
    SHIPPED = 'SHIPPED',
}

export interface Order {
    id: number;
    name: string;
    status: OrderStatus;
}

export interface OrderLine {
    id: number;
    qty: number;
    sku: Sku;
}
