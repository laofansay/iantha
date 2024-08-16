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

import { getEntities } from './baby-spec.reducer';

export const BabySpec = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const babySpecList = useAppSelector(state => state.babySpec.entities);
  const loading = useAppSelector(state => state.babySpec.loading);

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
      <h2 id="baby-spec-heading" data-cy="BabySpecHeading">
        <Translate contentKey="ianthaApp.babySpec.home.title">Baby Specs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ianthaApp.babySpec.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/baby-spec/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ianthaApp.babySpec.home.createLabel">Create new Baby Spec</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {babySpecList && babySpecList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="ianthaApp.babySpec.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('specCode')}>
                  <Translate contentKey="ianthaApp.babySpec.specCode">Spec Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('specCode')} />
                </th>
                <th className="hand" onClick={sort('specTitle')}>
                  <Translate contentKey="ianthaApp.babySpec.specTitle">Spec Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('specTitle')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="ianthaApp.babySpec.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('specQuantity')}>
                  <Translate contentKey="ianthaApp.babySpec.specQuantity">Spec Quantity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('specQuantity')} />
                </th>
                <th className="hand" onClick={sort('guidePrice')}>
                  <Translate contentKey="ianthaApp.babySpec.guidePrice">Guide Price</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('guidePrice')} />
                </th>
                <th className="hand" onClick={sort('specPrice')}>
                  <Translate contentKey="ianthaApp.babySpec.specPrice">Spec Price</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('specPrice')} />
                </th>
                <th className="hand" onClick={sort('showPrice')}>
                  <Translate contentKey="ianthaApp.babySpec.showPrice">Show Price</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('showPrice')} />
                </th>
                <th className="hand" onClick={sort('specStatus')}>
                  <Translate contentKey="ianthaApp.babySpec.specStatus">Spec Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('specStatus')} />
                </th>
                <th className="hand" onClick={sort('images')}>
                  <Translate contentKey="ianthaApp.babySpec.images">Images</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('images')} />
                </th>
                <th className="hand" onClick={sort('sellCount')}>
                  <Translate contentKey="ianthaApp.babySpec.sellCount">Sell Count</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('sellCount')} />
                </th>
                <th className="hand" onClick={sort('stockCount')}>
                  <Translate contentKey="ianthaApp.babySpec.stockCount">Stock Count</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('stockCount')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="ianthaApp.babySpec.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  <Translate contentKey="ianthaApp.babySpec.updatedAt">Updated At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th>
                  <Translate contentKey="ianthaApp.babySpec.products">Products</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {babySpecList.map((babySpec, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/baby-spec/${babySpec.id}`} color="link" size="sm">
                      {babySpec.id}
                    </Button>
                  </td>
                  <td>{babySpec.specCode}</td>
                  <td>{babySpec.specTitle}</td>
                  <td>{babySpec.description}</td>
                  <td>{babySpec.specQuantity}</td>
                  <td>{babySpec.guidePrice}</td>
                  <td>{babySpec.specPrice}</td>
                  <td>{babySpec.showPrice}</td>
                  <td>
                    <Translate contentKey={`ianthaApp.ProdStatus.${babySpec.specStatus}`} />
                  </td>
                  <td>{babySpec.images}</td>
                  <td>{babySpec.sellCount}</td>
                  <td>{babySpec.stockCount}</td>
                  <td>{babySpec.createdAt ? <TextFormat type="date" value={babySpec.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{babySpec.updatedAt ? <TextFormat type="date" value={babySpec.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{babySpec.products ? <Link to={`/product/${babySpec.products.id}`}>{babySpec.products.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/baby-spec/${babySpec.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/baby-spec/${babySpec.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/baby-spec/${babySpec.id}/delete`)}
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
              <Translate contentKey="ianthaApp.babySpec.home.notFound">No Baby Specs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BabySpec;
