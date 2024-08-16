import dayjs from 'dayjs';
import { IOrder } from 'app/shared/model/order.model';

export interface IDiscountCode {
  id?: number;
  code?: string;
  stock?: number;
  description?: string | null;
  percent?: number;
  maxDiscountAmount?: number;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs;
  createdAt?: dayjs.Dayjs;
  order?: IOrder | null;
}

export const defaultValue: Readonly<IDiscountCode> = {};
