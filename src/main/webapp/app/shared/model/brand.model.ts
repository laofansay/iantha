import { IProduct } from 'app/shared/model/product.model';

export interface IBrand {
  id?: number;
  title?: string;
  description?: string | null;
  logo?: string | null;
  products?: IProduct | null;
}

export const defaultValue: Readonly<IBrand> = {};
