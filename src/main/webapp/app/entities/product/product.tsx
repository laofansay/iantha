import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './product.reducer';

export const Product = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const productList = useAppSelector(state => state.product.entities);
  const loading = useAppSelector(state => state.product.loading);

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
      <h2 id="product-heading" data-cy="ProductHeading">
        <Translate contentKey="ianthaApp.product.home.title">Products</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ianthaApp.product.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/product/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ianthaApp.product.home.createLabel">Create new Product</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {productList && productList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="ianthaApp.product.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="ianthaApp.product.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('transCode')}>
                  <Translate contentKey="ianthaApp.product.transCode">Trans Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('transCode')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="ianthaApp.product.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('images')}>
                  <Translate contentKey="ianthaApp.product.images">Images</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('images')} />
                </th>
                <th className="hand" onClick={sort('keywords')}>
                  <Translate contentKey="ianthaApp.product.keywords">Keywords</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('keywords')} />
                </th>
                <th className="hand" onClick={sort('metadata')}>
                  <Translate contentKey="ianthaApp.product.metadata">Metadata</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('metadata')} />
                </th>
                <th className="hand" onClick={sort('guidePrice')}>
                  <Translate contentKey="ianthaApp.product.guidePrice">Guide Price</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('guidePrice')} />
                </th>
                <th className="hand" onClick={sort('price')}>
                  <Translate contentKey="ianthaApp.product.price">Price</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('price')} />
                </th>
                <th className="hand" onClick={sort('showPrice')}>
                  <Translate contentKey="ianthaApp.product.showPrice">Show Price</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('showPrice')} />
                </th>
                <th className="hand" onClick={sort('discount')}>
                  <Translate contentKey="ianthaApp.product.discount">Discount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('discount')} />
                </th>
                <th className="hand" onClick={sort('stock')}>
                  <Translate contentKey="ianthaApp.product.stock">Stock</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('stock')} />
                </th>
                <th className="hand" onClick={sort('isPhysical')}>
                  <Translate contentKey="ianthaApp.product.isPhysical">Is Physical</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isPhysical')} />
                </th>
                <th className="hand" onClick={sort('isAvailable')}>
                  <Translate contentKey="ianthaApp.product.isAvailable">Is Available</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isAvailable')} />
                </th>
                <th className="hand" onClick={sort('isFeatured')}>
                  <Translate contentKey="ianthaApp.product.isFeatured">Is Featured</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isFeatured')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="ianthaApp.product.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('sellCount')}>
                  <Translate contentKey="ianthaApp.product.sellCount">Sell Count</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('sellCount')} />
                </th>
                <th className="hand" onClick={sort('stockCount')}>
                  <Translate contentKey="ianthaApp.product.stockCount">Stock Count</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('stockCount')} />
                </th>
                <th className="hand" onClick={sort('shelvesStatus')}>
                  <Translate contentKey="ianthaApp.product.shelvesStatus">Shelves Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('shelvesStatus')} />
                </th>
                <th className="hand" onClick={sort('shelvesDate')}>
                  <Translate contentKey="ianthaApp.product.shelvesDate">Shelves Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('shelvesDate')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="ianthaApp.product.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  <Translate contentKey="ianthaApp.product.updatedAt">Updated At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th>
                  <Translate contentKey="ianthaApp.product.brand">Brand</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="ianthaApp.product.categories">Categories</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productList.map((product, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/product/${product.id}`} color="link" size="sm">
                      {product.id}
                    </Button>
                  </td>
                  <td>{product.title}</td>
                  <td>{product.transCode}</td>
                  <td>{product.description}</td>
                  <td>{product.images}</td>
                  <td>{product.keywords}</td>
                  <td>{product.metadata}</td>
                  <td>{product.guidePrice}</td>
                  <td>{product.price}</td>
                  <td>{product.showPrice}</td>
                  <td>{product.discount}</td>
                  <td>{product.stock}</td>
                  <td>{product.isPhysical ? 'true' : 'false'}</td>
                  <td>{product.isAvailable ? 'true' : 'false'}</td>
                  <td>{product.isFeatured ? 'true' : 'false'}</td>
                  <td>
                    <Translate contentKey={`ianthaApp.ProdStatus.${product.status}`} />
                  </td>
                  <td>{product.sellCount}</td>
                  <td>{product.stockCount}</td>
                  <td>{product.shelvesStatus ? 'true' : 'false'}</td>
                  <td>{product.shelvesDate ? <TextFormat type="date" value={product.shelvesDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{product.createdAt ? <TextFormat type="date" value={product.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{product.updatedAt ? <TextFormat type="date" value={product.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{product.brand ? <Link to={`/brand/${product.brand.id}`}>{product.brand.id}</Link> : ''}</td>
                  <td>{product.categories ? <Link to={`/category/${product.categories.id}`}>{product.categories.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/product/${product.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/product/${product.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/product/${product.id}/delete`)}
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
              <Translate contentKey="ianthaApp.product.home.notFound">No Products found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Product;
