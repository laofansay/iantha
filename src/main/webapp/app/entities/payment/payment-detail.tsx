import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './payment.reducer';

export const PaymentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const paymentEntity = useAppSelector(state => state.payment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paymentDetailsHeading">
          <Translate contentKey="ianthaApp.payment.detail.title">Payment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="ianthaApp.payment.number">Number</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.number}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="ianthaApp.payment.status">Status</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.status}</dd>
          <dt>
            <span id="refId">
              <Translate contentKey="ianthaApp.payment.refId">Ref Id</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.refId}</dd>
          <dt>
            <span id="cardPan">
              <Translate contentKey="ianthaApp.payment.cardPan">Card Pan</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.cardPan}</dd>
          <dt>
            <span id="cardHash">
              <Translate contentKey="ianthaApp.payment.cardHash">Card Hash</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.cardHash}</dd>
          <dt>
            <span id="fee">
              <Translate contentKey="ianthaApp.payment.fee">Fee</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.fee}</dd>
          <dt>
            <span id="isSuccessful">
              <Translate contentKey="ianthaApp.payment.isSuccessful">Is Successful</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.isSuccessful ? 'true' : 'false'}</dd>
          <dt>
            <span id="payable">
              <Translate contentKey="ianthaApp.payment.payable">Payable</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.payable}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="ianthaApp.payment.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.createdAt ? <TextFormat value={paymentEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="ianthaApp.payment.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.updatedAt ? <TextFormat value={paymentEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="ianthaApp.payment.order">Order</Translate>
          </dt>
          <dd>{paymentEntity.order ? paymentEntity.order.id : ''}</dd>
          <dt>
            <Translate contentKey="ianthaApp.payment.provider">Provider</Translate>
          </dt>
          <dd>{paymentEntity.provider ? paymentEntity.provider.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/payment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment/${paymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaymentDetail;
