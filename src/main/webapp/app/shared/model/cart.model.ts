import dayjs from 'dayjs';
import { ICartItem } from 'app/shared/model/cart-item.model';

export interface ICart {
  id?: number;
  ident?: number;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  items?: ICartItem | null;
}

export const defaultValue: Readonly<ICart> = {};
