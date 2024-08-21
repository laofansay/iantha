import { IProduct } from 'app/shared/model/product.model';
import { IOrder } from 'app/shared/model/order.model';

export interface IOrderItem {
  id?: number;
  count?: number;
  price?: number;
  discount?: number;
  product?: IProduct | null;
  orderItem?: IOrder | null;
}

export const defaultValue: Readonly<IOrderItem> = {};
