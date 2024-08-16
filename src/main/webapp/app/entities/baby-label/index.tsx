import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BabyLabel from './baby-label';
import BabyLabelDetail from './baby-label-detail';
import BabyLabelUpdate from './baby-label-update';
import BabyLabelDeleteDialog from './baby-label-delete-dialog';

const BabyLabelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BabyLabel />} />
    <Route path="new" element={<BabyLabelUpdate />} />
    <Route path=":id">
      <Route index element={<BabyLabelDetail />} />
      <Route path="edit" element={<BabyLabelUpdate />} />
      <Route path="delete" element={<BabyLabelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BabyLabelRoutes;
