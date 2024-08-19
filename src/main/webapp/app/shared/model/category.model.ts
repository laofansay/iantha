import dayjs from 'dayjs';

export interface ICategory {
  id?: number;
  title?: string;
  description?: string;
  cateCode?: string;
  icon?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
}

export const defaultValue: Readonly<ICategory> = {};
