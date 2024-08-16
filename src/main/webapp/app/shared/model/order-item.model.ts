export interface IOrderItem {
  id?: number;
  orderId?: string;
  productId?: string;
  count?: number;
  price?: number;
  discount?: number;
}

export const defaultValue: Readonly<IOrderItem> = {};
