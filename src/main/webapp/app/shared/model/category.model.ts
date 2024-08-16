import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';

export interface ICategory {
  id?: number;
  title?: string;
  description?: string;
  cateCode?: string;
  icon?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  products?: IProduct | null;
}

export const defaultValue: Readonly<ICategory> = {};
