export interface IPaymentProvider {
  id?: number;
  title?: string;
  description?: string | null;
  websiteUrl?: string | null;
  isActive?: boolean;
}

export const defaultValue: Readonly<IPaymentProvider> = {
  isActive: false,
};
