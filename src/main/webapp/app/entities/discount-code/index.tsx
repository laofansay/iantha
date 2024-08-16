import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DiscountCode from './discount-code';
import DiscountCodeDetail from './discount-code-detail';
import DiscountCodeUpdate from './discount-code-update';
import DiscountCodeDeleteDialog from './discount-code-delete-dialog';

const DiscountCodeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DiscountCode />} />
    <Route path="new" element={<DiscountCodeUpdate />} />
    <Route path=":id">
      <Route index element={<DiscountCodeDetail />} />
      <Route path="edit" element={<DiscountCodeUpdate />} />
      <Route path="delete" element={<DiscountCodeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DiscountCodeRoutes;
