export enum LocationType {
    STORAGE = 'STORAGE',
    SHIPDOCK = 'SHIPDOCK',
    INBOUND = 'INBOUND',
}

export interface WarehouseLocation {
    id: number;
    name: string;
    locationType: LocationType;
    capacity: number;
    usedCapacity: number;
    freeCapacity: number;
}
