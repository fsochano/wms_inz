export interface ColumnSchema<T> {
  key: keyof T;
  header: string;
  param?: keyof T | ((v: T) => any);
}
