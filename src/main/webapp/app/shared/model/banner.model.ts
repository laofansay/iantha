import dayjs from 'dayjs';

export interface IBanner {
  id?: number;
  label?: string;
  images?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IBanner> = {};
