import dayjs from 'dayjs';

export interface IAddress {
  id?: number;
  country?: string;
  address?: string;
  city?: string;
  phone?: string;
  postalCode?: string;
  createdAt?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IAddress> = {};
