import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './order.reducer';

export const Order = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const orderList = useAppSelector(state => state.order.entities);
  const loading = useAppSelector(state => state.order.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="order-heading" data-cy="OrderHeading">
        <Translate contentKey="ianthaApp.order.home.title">Orders</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ianthaApp.order.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/order/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ianthaApp.order.home.createLabel">Create new Order</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {orderList && orderList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="ianthaApp.order.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('number')}>
                  <Translate contentKey="ianthaApp.order.number">Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('number')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="ianthaApp.order.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('total')}>
                  <Translate contentKey="ianthaApp.order.total">Total</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('total')} />
                </th>
                <th className="hand" onClick={sort('shipping')}>
                  <Translate contentKey="ianthaApp.order.shipping">Shipping</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('shipping')} />
                </th>
                <th className="hand" onClick={sort('payable')}>
                  <Translate contentKey="ianthaApp.order.payable">Payable</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('payable')} />
                </th>
                <th className="hand" onClick={sort('tax')}>
                  <Translate contentKey="ianthaApp.order.tax">Tax</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('tax')} />
                </th>
                <th className="hand" onClick={sort('discount')}>
                  <Translate contentKey="ianthaApp.order.discount">Discount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('discount')} />
                </th>
                <th className="hand" onClick={sort('isPaid')}>
                  <Translate contentKey="ianthaApp.order.isPaid">Is Paid</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isPaid')} />
                </th>
                <th className="hand" onClick={sort('isCompleted')}>
                  <Translate contentKey="ianthaApp.order.isCompleted">Is Completed</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isCompleted')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="ianthaApp.order.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  <Translate contentKey="ianthaApp.order.updatedAt">Updated At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th>
                  <Translate contentKey="ianthaApp.order.refund">Refund</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {orderList.map((order, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/order/${order.id}`} color="link" size="sm">
                      {order.id}
                    </Button>
                  </td>
                  <td>{order.number}</td>
                  <td>{order.status}</td>
                  <td>{order.total}</td>
                  <td>{order.shipping}</td>
                  <td>{order.payable}</td>
                  <td>{order.tax}</td>
                  <td>{order.discount}</td>
                  <td>{order.isPaid ? 'true' : 'false'}</td>
                  <td>{order.isCompleted ? 'true' : 'false'}</td>
                  <td>{order.createdAt ? <TextFormat type="date" value={order.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{order.updatedAt ? <TextFormat type="date" value={order.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{order.refund ? <Link to={`/refund/${order.refund.id}`}>{order.refund.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/order/${order.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/order/${order.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/order/${order.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="ianthaApp.order.home.notFound">No Orders found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Order;
