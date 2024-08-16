import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';
import { ProdStatus } from 'app/shared/model/enumerations/prod-status.model';

export interface IBabySpec {
  id?: number;
  specCode?: string;
  specTitle?: string;
  description?: string;
  specQuantity?: number;
  guidePrice?: number;
  specPrice?: number;
  showPrice?: number;
  specStatus?: keyof typeof ProdStatus;
  images?: string;
  sellCount?: number | null;
  stockCount?: number | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  products?: IProduct | null;
}

export const defaultValue: Readonly<IBabySpec> = {};
