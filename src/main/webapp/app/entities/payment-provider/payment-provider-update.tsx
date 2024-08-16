import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPaymentProvider } from 'app/shared/model/payment-provider.model';
import { getEntity, updateEntity, createEntity, reset } from './payment-provider.reducer';

export const PaymentProviderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const paymentProviderEntity = useAppSelector(state => state.paymentProvider.entity);
  const loading = useAppSelector(state => state.paymentProvider.loading);
  const updating = useAppSelector(state => state.paymentProvider.updating);
  const updateSuccess = useAppSelector(state => state.paymentProvider.updateSuccess);

  const handleClose = () => {
    navigate('/payment-provider');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...paymentProviderEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...paymentProviderEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ianthaApp.paymentProvider.home.createOrEditLabel" data-cy="PaymentProviderCreateUpdateHeading">
            <Translate contentKey="ianthaApp.paymentProvider.home.createOrEditLabel">Create or edit a PaymentProvider</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="payment-provider-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ianthaApp.paymentProvider.title')}
                id="payment-provider-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.paymentProvider.description')}
                id="payment-provider-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('ianthaApp.paymentProvider.websiteUrl')}
                id="payment-provider-websiteUrl"
                name="websiteUrl"
                data-cy="websiteUrl"
                type="text"
              />
              <ValidatedField
                label={translate('ianthaApp.paymentProvider.isActive')}
                id="payment-provider-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payment-provider" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PaymentProviderUpdate;
