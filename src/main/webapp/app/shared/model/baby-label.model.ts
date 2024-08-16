import { IProduct } from 'app/shared/model/product.model';
import { LabelCate } from 'app/shared/model/enumerations/label-cate.model';

export interface IBabyLabel {
  id?: number;
  title?: string;
  labelCate?: keyof typeof LabelCate;
  labelCode?: string;
  labelAttr?: string;
  identify?: string;
  ruleReadme?: string | null;
  ruleExpression?: string | null;
  products?: IProduct | null;
}

export const defaultValue: Readonly<IBabyLabel> = {};
