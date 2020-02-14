import { IBranch } from 'app/shared/model//branch.model';

export interface IDepartment {
  id?: number;
  departmentName?: string;
  phone?: string;
  email?: string;
  branch?: IBranch;
}

export const defaultValue: Readonly<IDepartment> = {};
