import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './baby-label.reducer';

export const BabyLabel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const babyLabelList = useAppSelector(state => state.babyLabel.entities);
  const loading = useAppSelector(state => state.babyLabel.loading);

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
      <h2 id="baby-label-heading" data-cy="BabyLabelHeading">
        <Translate contentKey="ianthaApp.babyLabel.home.title">Baby Labels</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ianthaApp.babyLabel.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/baby-label/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ianthaApp.babyLabel.home.createLabel">Create new Baby Label</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {babyLabelList && babyLabelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="ianthaApp.babyLabel.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="ianthaApp.babyLabel.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('labelCate')}>
                  <Translate contentKey="ianthaApp.babyLabel.labelCate">Label Cate</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('labelCate')} />
                </th>
                <th className="hand" onClick={sort('labelCode')}>
                  <Translate contentKey="ianthaApp.babyLabel.labelCode">Label Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('labelCode')} />
                </th>
                <th className="hand" onClick={sort('labelAttr')}>
                  <Translate contentKey="ianthaApp.babyLabel.labelAttr">Label Attr</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('labelAttr')} />
                </th>
                <th className="hand" onClick={sort('identify')}>
                  <Translate contentKey="ianthaApp.babyLabel.identify">Identify</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('identify')} />
                </th>
                <th className="hand" onClick={sort('ruleReadme')}>
                  <Translate contentKey="ianthaApp.babyLabel.ruleReadme">Rule Readme</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('ruleReadme')} />
                </th>
                <th className="hand" onClick={sort('ruleExpression')}>
                  <Translate contentKey="ianthaApp.babyLabel.ruleExpression">Rule Expression</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('ruleExpression')} />
                </th>
                <th>
                  <Translate contentKey="ianthaApp.babyLabel.products">Products</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {babyLabelList.map((babyLabel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/baby-label/${babyLabel.id}`} color="link" size="sm">
                      {babyLabel.id}
                    </Button>
                  </td>
                  <td>{babyLabel.title}</td>
                  <td>
                    <Translate contentKey={`ianthaApp.LabelCate.${babyLabel.labelCate}`} />
                  </td>
                  <td>{babyLabel.labelCode}</td>
                  <td>{babyLabel.labelAttr}</td>
                  <td>{babyLabel.identify}</td>
                  <td>{babyLabel.ruleReadme}</td>
                  <td>{babyLabel.ruleExpression}</td>
                  <td>{babyLabel.products ? <Link to={`/product/${babyLabel.products.id}`}>{babyLabel.products.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/baby-label/${babyLabel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/baby-label/${babyLabel.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/baby-label/${babyLabel.id}/delete`)}
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
              <Translate contentKey="ianthaApp.babyLabel.home.notFound">No Baby Labels found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BabyLabel;
