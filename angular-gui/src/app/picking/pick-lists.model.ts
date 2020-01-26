export type PickListStatus = 'RELEASED' | 'IN_PROGRESS' | 'COMPLETED' | 'SHIPPED';

export interface PickList {
    id: number;
    status: PickListStatus,
}
