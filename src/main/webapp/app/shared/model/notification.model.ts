import dayjs from 'dayjs';

export interface INotification {
  id?: number;
  ident?: string;
  content?: string;
  isRead?: boolean;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
}

export const defaultValue: Readonly<INotification> = {
  isRead: false,
};
