import { IDepartment } from 'app/shared/model//department.model';
import { IStatus } from 'app/shared/model//status.model';

export interface IComplain {
  id?: number;
  details?: string;
  imgContentType?: string;
  img?: any;
  expection?: string;
  showAnonymous?: boolean;
  escalate?: string;
  resolution?: string;
  department?: IDepartment;
  status?: IStatus;
}

export const defaultValue: Readonly<IComplain> = {
  showAnonymous: false
};
