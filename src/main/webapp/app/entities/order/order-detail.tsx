import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order.reducer';

export const OrderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderEntity = useAppSelector(state => state.order.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderDetailsHeading">
          <Translate contentKey="ianthaApp.order.detail.title">Order</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="ianthaApp.order.number">Number</Translate>
            </span>
          </dt>
          <dd>{orderEntity.number}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="ianthaApp.order.status">Status</Translate>
            </span>
          </dt>
          <dd>{orderEntity.status}</dd>
          <dt>
            <span id="total">
              <Translate contentKey="ianthaApp.order.total">Total</Translate>
            </span>
          </dt>
          <dd>{orderEntity.total}</dd>
          <dt>
            <span id="shipping">
              <Translate contentKey="ianthaApp.order.shipping">Shipping</Translate>
            </span>
          </dt>
          <dd>{orderEntity.shipping}</dd>
          <dt>
            <span id="payable">
              <Translate contentKey="ianthaApp.order.payable">Payable</Translate>
            </span>
          </dt>
          <dd>{orderEntity.payable}</dd>
          <dt>
            <span id="tax">
              <Translate contentKey="ianthaApp.order.tax">Tax</Translate>
            </span>
          </dt>
          <dd>{orderEntity.tax}</dd>
          <dt>
            <span id="discount">
              <Translate contentKey="ianthaApp.order.discount">Discount</Translate>
            </span>
          </dt>
          <dd>{orderEntity.discount}</dd>
          <dt>
            <span id="isPaid">
              <Translate contentKey="ianthaApp.order.isPaid">Is Paid</Translate>
            </span>
          </dt>
          <dd>{orderEntity.isPaid ? 'true' : 'false'}</dd>
          <dt>
            <span id="isCompleted">
              <Translate contentKey="ianthaApp.order.isCompleted">Is Completed</Translate>
            </span>
          </dt>
          <dd>{orderEntity.isCompleted ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="ianthaApp.order.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{orderEntity.createdAt ? <TextFormat value={orderEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="ianthaApp.order.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{orderEntity.updatedAt ? <TextFormat value={orderEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="ianthaApp.order.refund">Refund</Translate>
          </dt>
          <dd>{orderEntity.refund ? orderEntity.refund.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order/${orderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderDetail;
