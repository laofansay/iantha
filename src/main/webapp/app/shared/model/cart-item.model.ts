import { IProduct } from 'app/shared/model/product.model';

export interface ICartItem {
  id?: number;
  cid?: string;
  prodId?: string;
  count?: number;
  product?: IProduct | null;
}

export const defaultValue: Readonly<ICartItem> = {};
