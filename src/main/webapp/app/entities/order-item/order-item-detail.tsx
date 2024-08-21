import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order-item.reducer';

export const OrderItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderItemEntity = useAppSelector(state => state.orderItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderItemDetailsHeading">
          <Translate contentKey="ianthaApp.orderItem.detail.title">OrderItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.id}</dd>
          <dt>
            <span id="count">
              <Translate contentKey="ianthaApp.orderItem.count">Count</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.count}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="ianthaApp.orderItem.price">Price</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.price}</dd>
          <dt>
            <span id="discount">
              <Translate contentKey="ianthaApp.orderItem.discount">Discount</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.discount}</dd>
          <dt>
            <Translate contentKey="ianthaApp.orderItem.product">Product</Translate>
          </dt>
          <dd>{orderItemEntity.product ? orderItemEntity.product.id : ''}</dd>
          <dt>
            <Translate contentKey="ianthaApp.orderItem.orderItem">Order Item</Translate>
          </dt>
          <dd>{orderItemEntity.orderItem ? orderItemEntity.orderItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-item/${orderItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderItemDetail;
