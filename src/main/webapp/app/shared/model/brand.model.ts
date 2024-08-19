export interface IBrand {
  id?: number;
  title?: string;
  description?: string | null;
  logo?: string | null;
}

export const defaultValue: Readonly<IBrand> = {};
