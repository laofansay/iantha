import dayjs from 'dayjs';
import { IOrder } from 'app/shared/model/order.model';
import { IPaymentProvider } from 'app/shared/model/payment-provider.model';

export interface IPayment {
  id?: number;
  number?: number;
  status?: string;
  refId?: string;
  cardPan?: string | null;
  cardHash?: string | null;
  fee?: number | null;
  isSuccessful?: boolean;
  payable?: number;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  order?: IOrder | null;
  provider?: IPaymentProvider | null;
}

export const defaultValue: Readonly<IPayment> = {
  isSuccessful: false,
};
