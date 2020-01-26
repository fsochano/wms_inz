export interface ColumnSchema<T> {
    name: String;
    param: keyof T;
}