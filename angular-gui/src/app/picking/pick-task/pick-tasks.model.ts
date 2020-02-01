import { Auditable } from './../../shared/entity.model';
export type PickTaskStatus = 'READY' | 'PICKED' | 'COMPLETED' | 'SHIPPED';

export interface PickTask extends Auditable {
    id: number;
    qty: number,
    status: PickTaskStatus
    skuName: string;
    fromLocationName?: string,
    fromContainerId?: number,
    toLocationName?: string,
    toContainerId?: number,
}
