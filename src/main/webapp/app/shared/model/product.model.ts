import dayjs from 'dayjs';
import { IBrand } from 'app/shared/model/brand.model';
import { ICategory } from 'app/shared/model/category.model';
import { ProdStatus } from 'app/shared/model/enumerations/prod-status.model';

export interface IProduct {
  id?: number;
  title?: string;
  transCode?: string;
  description?: string | null;
  images?: string | null;
  keywords?: string | null;
  metadata?: string | null;
  guidePrice?: number;
  price?: number;
  showPrice?: number;
  discount?: number;
  stock?: number;
  isPhysical?: boolean;
  isAvailable?: boolean;
  isFeatured?: boolean;
  status?: keyof typeof ProdStatus | null;
  sellCount?: number | null;
  stockCount?: number | null;
  shelvesStatus?: boolean | null;
  shelvesDate?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  brand?: IBrand | null;
  categories?: ICategory | null;
}

export const defaultValue: Readonly<IProduct> = {
  isPhysical: false,
  isAvailable: false,
  isFeatured: false,
  shelvesStatus: false,
};
