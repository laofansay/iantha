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

import { getEntities } from './discount-code.reducer';

export const DiscountCode = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const discountCodeList = useAppSelector(state => state.discountCode.entities);
  const loading = useAppSelector(state => state.discountCode.loading);

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
      <h2 id="discount-code-heading" data-cy="DiscountCodeHeading">
        <Translate contentKey="ianthaApp.discountCode.home.title">Discount Codes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ianthaApp.discountCode.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/discount-code/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ianthaApp.discountCode.home.createLabel">Create new Discount Code</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {discountCodeList && discountCodeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="ianthaApp.discountCode.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="ianthaApp.discountCode.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('stock')}>
                  <Translate contentKey="ianthaApp.discountCode.stock">Stock</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('stock')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="ianthaApp.discountCode.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('percent')}>
                  <Translate contentKey="ianthaApp.discountCode.percent">Percent</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('percent')} />
                </th>
                <th className="hand" onClick={sort('maxDiscountAmount')}>
                  <Translate contentKey="ianthaApp.discountCode.maxDiscountAmount">Max Discount Amount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('maxDiscountAmount')} />
                </th>
                <th className="hand" onClick={sort('startDate')}>
                  <Translate contentKey="ianthaApp.discountCode.startDate">Start Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startDate')} />
                </th>
                <th className="hand" onClick={sort('endDate')}>
                  <Translate contentKey="ianthaApp.discountCode.endDate">End Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endDate')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="ianthaApp.discountCode.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th>
                  <Translate contentKey="ianthaApp.discountCode.order">Order</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {discountCodeList.map((discountCode, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/discount-code/${discountCode.id}`} color="link" size="sm">
                      {discountCode.id}
                    </Button>
                  </td>
                  <td>{discountCode.code}</td>
                  <td>{discountCode.stock}</td>
                  <td>{discountCode.description}</td>
                  <td>{discountCode.percent}</td>
                  <td>{discountCode.maxDiscountAmount}</td>
                  <td>
                    {discountCode.startDate ? <TextFormat type="date" value={discountCode.startDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{discountCode.endDate ? <TextFormat type="date" value={discountCode.endDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {discountCode.createdAt ? <TextFormat type="date" value={discountCode.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{discountCode.order ? <Link to={`/order/${discountCode.order.id}`}>{discountCode.order.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/discount-code/${discountCode.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/discount-code/${discountCode.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/discount-code/${discountCode.id}/delete`)}
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
              <Translate contentKey="ianthaApp.discountCode.home.notFound">No Discount Codes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DiscountCode;
