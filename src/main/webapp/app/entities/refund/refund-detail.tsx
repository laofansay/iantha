import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './refund.reducer';

export const RefundDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const refundEntity = useAppSelector(state => state.refund.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="refundDetailsHeading">
          <Translate contentKey="ianthaApp.refund.detail.title">Refund</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{refundEntity.id}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="ianthaApp.refund.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{refundEntity.amount}</dd>
          <dt>
            <span id="reason">
              <Translate contentKey="ianthaApp.refund.reason">Reason</Translate>
            </span>
          </dt>
          <dd>{refundEntity.reason}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="ianthaApp.refund.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{refundEntity.createdAt ? <TextFormat value={refundEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="ianthaApp.refund.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{refundEntity.updatedAt ? <TextFormat value={refundEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/refund" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/refund/${refundEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RefundDetail;
