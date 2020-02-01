export interface Auditable {
  createdBy: string | null;
  createdDate: string;
  lastModifiedBy: string | null;
  lastModifiedDate: string;
}
