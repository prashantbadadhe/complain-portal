import { ILocation } from 'app/shared/model//location.model';
import { ICompany } from 'app/shared/model//company.model';

export interface IBranch {
  id?: number;
  branchName?: string;
  phone?: string;
  email?: string;
  location?: ILocation;
  company?: ICompany;
}

export const defaultValue: Readonly<IBranch> = {};
