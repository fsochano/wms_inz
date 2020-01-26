export type PickTaskStatus = 'READY' | 'PICKED' | 'COMPLETED' | 'SHIPPED';

export interface PickTask {
    id: number;
    qty: number,
    status: PickTaskStatus
    fromLocation?: string,
    fromContainer?: number,
    toLocation?: string,
    toContainer?: number,
}