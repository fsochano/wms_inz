export enum OrderStatus {
    HOLD = "HOLD",
    RELEASED = "RELEASED",
    PICKED = "PICKED",
}

export interface Order {
    id: number;
    name: string;
    status: OrderStatus;
}

export interface OrderLine {
    id: number;
    qty: number;
    item: string;
}

