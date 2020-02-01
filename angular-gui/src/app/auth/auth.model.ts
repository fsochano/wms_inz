
export type Authority = 'ORDERING' | 'PICKING' | 'SHIPPING' | 'SETTINGS';

export interface User {
    username: string;
    token: string;
    authorities: Authority[];
}
