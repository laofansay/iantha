import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './category.reducer';

export const CategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const categoryEntity = useAppSelector(state => state.category.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoryDetailsHeading">
          <Translate contentKey="ianthaApp.category.detail.title">Category</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="ianthaApp.category.title">Title</Translate>
            </span>
            <UncontrolledTooltip target="title">
              <Translate contentKey="ianthaApp.category.help.title" />
            </UncontrolledTooltip>
          </dt>
          <dd>{categoryEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="ianthaApp.category.description">Description</Translate>
            </span>
            <UncontrolledTooltip target="description">
              <Translate contentKey="ianthaApp.category.help.description" />
            </UncontrolledTooltip>
          </dt>
          <dd>{categoryEntity.description}</dd>
          <dt>
            <span id="cateCode">
              <Translate contentKey="ianthaApp.category.cateCode">Cate Code</Translate>
            </span>
            <UncontrolledTooltip target="cateCode">
              <Translate contentKey="ianthaApp.category.help.cateCode" />
            </UncontrolledTooltip>
          </dt>
          <dd>{categoryEntity.cateCode}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="ianthaApp.category.icon">Icon</Translate>
            </span>
            <UncontrolledTooltip target="icon">
              <Translate contentKey="ianthaApp.category.help.icon" />
            </UncontrolledTooltip>
          </dt>
          <dd>{categoryEntity.icon}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="ianthaApp.category.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.createdAt ? <TextFormat value={categoryEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="ianthaApp.category.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.updatedAt ? <TextFormat value={categoryEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/category/${categoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategoryDetail;
