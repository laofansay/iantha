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

import { getEntities } from './wishlist.reducer';

export const Wishlist = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const wishlistList = useAppSelector(state => state.wishlist.entities);
  const loading = useAppSelector(state => state.wishlist.loading);

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
      <h2 id="wishlist-heading" data-cy="WishlistHeading">
        <Translate contentKey="ianthaApp.wishlist.home.title">Wishlists</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ianthaApp.wishlist.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/wishlist/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ianthaApp.wishlist.home.createLabel">Create new Wishlist</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {wishlistList && wishlistList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="ianthaApp.wishlist.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('identify')}>
                  <Translate contentKey="ianthaApp.wishlist.identify">Identify</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('identify')} />
                </th>
                <th className="hand" onClick={sort('bizCode')}>
                  <Translate contentKey="ianthaApp.wishlist.bizCode">Biz Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bizCode')} />
                </th>
                <th className="hand" onClick={sort('bizDesc')}>
                  <Translate contentKey="ianthaApp.wishlist.bizDesc">Biz Desc</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bizDesc')} />
                </th>
                <th className="hand" onClick={sort('bizIcon')}>
                  <Translate contentKey="ianthaApp.wishlist.bizIcon">Biz Icon</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bizIcon')} />
                </th>
                <th className="hand" onClick={sort('bizTitle')}>
                  <Translate contentKey="ianthaApp.wishlist.bizTitle">Biz Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bizTitle')} />
                </th>
                <th className="hand" onClick={sort('link')}>
                  <Translate contentKey="ianthaApp.wishlist.link">Link</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('link')} />
                </th>
                <th className="hand" onClick={sort('favCate')}>
                  <Translate contentKey="ianthaApp.wishlist.favCate">Fav Cate</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('favCate')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="ianthaApp.wishlist.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {wishlistList.map((wishlist, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/wishlist/${wishlist.id}`} color="link" size="sm">
                      {wishlist.id}
                    </Button>
                  </td>
                  <td>{wishlist.identify}</td>
                  <td>{wishlist.bizCode}</td>
                  <td>{wishlist.bizDesc}</td>
                  <td>{wishlist.bizIcon}</td>
                  <td>{wishlist.bizTitle}</td>
                  <td>{wishlist.link}</td>
                  <td>
                    <Translate contentKey={`ianthaApp.FavCate.${wishlist.favCate}`} />
                  </td>
                  <td>{wishlist.createdDate ? <TextFormat type="date" value={wishlist.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/wishlist/${wishlist.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/wishlist/${wishlist.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/wishlist/${wishlist.id}/delete`)}
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
              <Translate contentKey="ianthaApp.wishlist.home.notFound">No Wishlists found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Wishlist;
