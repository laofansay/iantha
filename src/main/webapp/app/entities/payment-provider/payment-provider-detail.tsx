import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './payment-provider.reducer';

export const PaymentProviderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const paymentProviderEntity = useAppSelector(state => state.paymentProvider.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paymentProviderDetailsHeading">
          <Translate contentKey="ianthaApp.paymentProvider.detail.title">PaymentProvider</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{paymentProviderEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="ianthaApp.paymentProvider.title">Title</Translate>
            </span>
          </dt>
          <dd>{paymentProviderEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="ianthaApp.paymentProvider.description">Description</Translate>
            </span>
          </dt>
          <dd>{paymentProviderEntity.description}</dd>
          <dt>
            <span id="websiteUrl">
              <Translate contentKey="ianthaApp.paymentProvider.websiteUrl">Website Url</Translate>
            </span>
          </dt>
          <dd>{paymentProviderEntity.websiteUrl}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="ianthaApp.paymentProvider.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{paymentProviderEntity.isActive ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/payment-provider" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment-provider/${paymentProviderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaymentProviderDetail;
