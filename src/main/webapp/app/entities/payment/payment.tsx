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

import { getEntities } from './payment.reducer';

export const Payment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const paymentList = useAppSelector(state => state.payment.entities);
  const loading = useAppSelector(state => state.payment.loading);

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
      <h2 id="payment-heading" data-cy="PaymentHeading">
        <Translate contentKey="ianthaApp.payment.home.title">Payments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ianthaApp.payment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/payment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ianthaApp.payment.home.createLabel">Create new Payment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {paymentList && paymentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="ianthaApp.payment.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('number')}>
                  <Translate contentKey="ianthaApp.payment.number">Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('number')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="ianthaApp.payment.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('refId')}>
                  <Translate contentKey="ianthaApp.payment.refId">Ref Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('refId')} />
                </th>
                <th className="hand" onClick={sort('cardPan')}>
                  <Translate contentKey="ianthaApp.payment.cardPan">Card Pan</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cardPan')} />
                </th>
                <th className="hand" onClick={sort('cardHash')}>
                  <Translate contentKey="ianthaApp.payment.cardHash">Card Hash</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cardHash')} />
                </th>
                <th className="hand" onClick={sort('fee')}>
                  <Translate contentKey="ianthaApp.payment.fee">Fee</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('fee')} />
                </th>
                <th className="hand" onClick={sort('isSuccessful')}>
                  <Translate contentKey="ianthaApp.payment.isSuccessful">Is Successful</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isSuccessful')} />
                </th>
                <th className="hand" onClick={sort('payable')}>
                  <Translate contentKey="ianthaApp.payment.payable">Payable</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('payable')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="ianthaApp.payment.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  <Translate contentKey="ianthaApp.payment.updatedAt">Updated At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th>
                  <Translate contentKey="ianthaApp.payment.order">Order</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="ianthaApp.payment.provider">Provider</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {paymentList.map((payment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/payment/${payment.id}`} color="link" size="sm">
                      {payment.id}
                    </Button>
                  </td>
                  <td>{payment.number}</td>
                  <td>{payment.status}</td>
                  <td>{payment.refId}</td>
                  <td>{payment.cardPan}</td>
                  <td>{payment.cardHash}</td>
                  <td>{payment.fee}</td>
                  <td>{payment.isSuccessful ? 'true' : 'false'}</td>
                  <td>{payment.payable}</td>
                  <td>{payment.createdAt ? <TextFormat type="date" value={payment.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{payment.updatedAt ? <TextFormat type="date" value={payment.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{payment.order ? <Link to={`/order/${payment.order.id}`}>{payment.order.id}</Link> : ''}</td>
                  <td>{payment.provider ? <Link to={`/payment-provider/${payment.provider.id}`}>{payment.provider.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/payment/${payment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/payment/${payment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/payment/${payment.id}/delete`)}
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
              <Translate contentKey="ianthaApp.payment.home.notFound">No Payments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Payment;
