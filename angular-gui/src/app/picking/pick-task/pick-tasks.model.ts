export type PickTaskStatus = 'READY' | 'PICKED' | 'COMPLETED' | 'SHIPPED';

export interface PickTask {
    id: number;
    qty: number,
    status: PickTaskStatus
    fromLocationName?: string,
    fromContainerId?: number,
    toLocationName?: string,
    toContainerId?: number,
}