import dayjs from 'dayjs';
import { IRefund } from 'app/shared/model/refund.model';

export interface IOrder {
  id?: number;
  number?: number;
  status?: string;
  total?: number;
  shipping?: number;
  payable?: number;
  tax?: number;
  discount?: number;
  isPaid?: boolean;
  isCompleted?: boolean;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  refund?: IRefund | null;
}

export const defaultValue: Readonly<IOrder> = {
  isPaid: false,
  isCompleted: false,
};
