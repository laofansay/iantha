import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BabySpec from './baby-spec';
import BabySpecDetail from './baby-spec-detail';
import BabySpecUpdate from './baby-spec-update';
import BabySpecDeleteDialog from './baby-spec-delete-dialog';

const BabySpecRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BabySpec />} />
    <Route path="new" element={<BabySpecUpdate />} />
    <Route path=":id">
      <Route index element={<BabySpecDetail />} />
      <Route path="edit" element={<BabySpecUpdate />} />
      <Route path="delete" element={<BabySpecDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BabySpecRoutes;
