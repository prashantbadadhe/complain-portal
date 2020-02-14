import { ILocation } from 'app/shared/model//location.model';

export interface ICompany {
  id?: number;
  companyName?: string;
  phone?: string;
  email?: string;
  location?: ILocation;
}

export const defaultValue: Readonly<ICompany> = {};
