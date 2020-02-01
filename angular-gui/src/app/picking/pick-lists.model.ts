import { Auditable } from '../shared/entity.model';

export type PickListStatus = 'RELEASED' | 'IN_PROGRESS' | 'COMPLETED' | 'SHIPPED';

export interface PickList extends Auditable {
    id: number;
    status: PickListStatus,
}
