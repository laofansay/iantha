import dayjs from 'dayjs';
import { FavCate } from 'app/shared/model/enumerations/fav-cate.model';

export interface IWishlist {
  id?: number;
  identify?: string;
  bizCode?: string;
  bizDesc?: string;
  bizIcon?: string;
  bizTitle?: string;
  link?: string | null;
  favCate?: keyof typeof FavCate;
  createdDate?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IWishlist> = {};
