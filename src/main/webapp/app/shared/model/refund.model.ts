import dayjs from 'dayjs';

export interface IRefund {
  id?: number;
  amount?: number;
  reason?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IRefund> = {};
