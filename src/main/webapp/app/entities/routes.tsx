import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cart from './cart';
import CartItem from './cart-item';
import Brand from './brand';
import Product from './product';
import Category from './category';
import BabySpec from './baby-spec';
import BabyLabel from './baby-label';
import Order from './order';
import OrderItem from './order-item';
import Address from './address';
import Notification from './notification';
import DiscountCode from './discount-code';
import Refund from './refund';
import Payment from './payment';
import PaymentProvider from './payment-provider';
import Banner from './banner';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="cart/*" element={<Cart />} />
        <Route path="cart-item/*" element={<CartItem />} />
        <Route path="brand/*" element={<Brand />} />
        <Route path="product/*" element={<Product />} />
        <Route path="category/*" element={<Category />} />
        <Route path="baby-spec/*" element={<BabySpec />} />
        <Route path="baby-label/*" element={<BabyLabel />} />
        <Route path="order/*" element={<Order />} />
        <Route path="order-item/*" element={<OrderItem />} />
        <Route path="address/*" element={<Address />} />
        <Route path="notification/*" element={<Notification />} />
        <Route path="discount-code/*" element={<DiscountCode />} />
        <Route path="refund/*" element={<Refund />} />
        <Route path="payment/*" element={<Payment />} />
        <Route path="payment-provider/*" element={<PaymentProvider />} />
        <Route path="banner/*" element={<Banner />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
