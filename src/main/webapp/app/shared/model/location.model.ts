export interface ILocation {
  id?: number;
  area?: string;
  addressLineOne?: string;
  addressLineTwo?: string;
  buildingName?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  country?: string;
}

export const defaultValue: Readonly<ILocation> = {};
