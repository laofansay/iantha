import cartItem from 'app/entities/cart-item/cart-item.reducer';
import brand from 'app/entities/brand/brand.reducer';
import product from 'app/entities/product/product.reducer';
import category from 'app/entities/category/category.reducer';
import babySpec from 'app/entities/baby-spec/baby-spec.reducer';
import babyLabel from 'app/entities/baby-label/baby-label.reducer';
import order from 'app/entities/order/order.reducer';
import orderItem from 'app/entities/order-item/order-item.reducer';
import address from 'app/entities/address/address.reducer';
import notification from 'app/entities/notification/notification.reducer';
import discountCode from 'app/entities/discount-code/discount-code.reducer';
import refund from 'app/entities/refund/refund.reducer';
import payment from 'app/entities/payment/payment.reducer';
import paymentProvider from 'app/entities/payment-provider/payment-provider.reducer';
import banner from 'app/entities/banner/banner.reducer';
import wishlist from 'app/entities/wishlist/wishlist.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  cartItem,
  brand,
  product,
  category,
  babySpec,
  babyLabel,
  order,
  orderItem,
  address,
  notification,
  discountCode,
  refund,
  payment,
  paymentProvider,
  banner,
  wishlist,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
