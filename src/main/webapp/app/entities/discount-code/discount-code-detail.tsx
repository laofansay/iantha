import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './discount-code.reducer';

export const DiscountCodeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const discountCodeEntity = useAppSelector(state => state.discountCode.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="discountCodeDetailsHeading">
          <Translate contentKey="ianthaApp.discountCode.detail.title">DiscountCode</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{discountCodeEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="ianthaApp.discountCode.code">Code</Translate>
            </span>
          </dt>
          <dd>{discountCodeEntity.code}</dd>
          <dt>
            <span id="stock">
              <Translate contentKey="ianthaApp.discountCode.stock">Stock</Translate>
            </span>
          </dt>
          <dd>{discountCodeEntity.stock}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="ianthaApp.discountCode.description">Description</Translate>
            </span>
          </dt>
          <dd>{discountCodeEntity.description}</dd>
          <dt>
            <span id="percent">
              <Translate contentKey="ianthaApp.discountCode.percent">Percent</Translate>
            </span>
          </dt>
          <dd>{discountCodeEntity.percent}</dd>
          <dt>
            <span id="maxDiscountAmount">
              <Translate contentKey="ianthaApp.discountCode.maxDiscountAmount">Max Discount Amount</Translate>
            </span>
          </dt>
          <dd>{discountCodeEntity.maxDiscountAmount}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="ianthaApp.discountCode.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {discountCodeEntity.startDate ? <TextFormat value={discountCodeEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="ianthaApp.discountCode.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {discountCodeEntity.endDate ? <TextFormat value={discountCodeEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="ianthaApp.discountCode.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {discountCodeEntity.createdAt ? <TextFormat value={discountCodeEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="ianthaApp.discountCode.order">Order</Translate>
          </dt>
          <dd>{discountCodeEntity.order ? discountCodeEntity.order.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/discount-code" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/discount-code/${discountCodeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DiscountCodeDetail;
