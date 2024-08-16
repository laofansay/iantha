import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrder } from 'app/shared/model/order.model';
import { getEntities as getOrders } from 'app/entities/order/order.reducer';
import { IDiscountCode } from 'app/shared/model/discount-code.model';
import { getEntity, updateEntity, createEntity, reset } from './discount-code.reducer';

export const DiscountCodeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const orders = useAppSelector(state => state.order.entities);
  const discountCodeEntity = useAppSelector(state => state.discountCode.entity);
  const loading = useAppSelector(state => state.discountCode.loading);
  const updating = useAppSelector(state => state.discountCode.updating);
  const updateSuccess = useAppSelector(state => state.discountCode.updateSuccess);

  const handleClose = () => {
    navigate('/discount-code');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOrders({}));
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
    if (values.stock !== undefined && typeof values.stock !== 'number') {
      values.stock = Number(values.stock);
    }
    if (values.percent !== undefined && typeof values.percent !== 'number') {
      values.percent = Number(values.percent);
    }
    if (values.maxDiscountAmount !== undefined && typeof values.maxDiscountAmount !== 'number') {
      values.maxDiscountAmount = Number(values.maxDiscountAmount);
    }
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...discountCodeEntity,
      ...values,
      order: orders.find(it => it.id.toString() === values.order?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          startDate: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
        }
      : {
          ...discountCodeEntity,
          startDate: convertDateTimeFromServer(discountCodeEntity.startDate),
          endDate: convertDateTimeFromServer(discountCodeEntity.endDate),
          createdAt: convertDateTimeFromServer(discountCodeEntity.createdAt),
          order: discountCodeEntity?.order?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ianthaApp.discountCode.home.createOrEditLabel" data-cy="DiscountCodeCreateUpdateHeading">
            <Translate contentKey="ianthaApp.discountCode.home.createOrEditLabel">Create or edit a DiscountCode</Translate>
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
                  id="discount-code-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ianthaApp.discountCode.code')}
                id="discount-code-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.discountCode.stock')}
                id="discount-code-stock"
                name="stock"
                data-cy="stock"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.discountCode.description')}
                id="discount-code-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('ianthaApp.discountCode.percent')}
                id="discount-code-percent"
                name="percent"
                data-cy="percent"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.discountCode.maxDiscountAmount')}
                id="discount-code-maxDiscountAmount"
                name="maxDiscountAmount"
                data-cy="maxDiscountAmount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.discountCode.startDate')}
                id="discount-code-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.discountCode.endDate')}
                id="discount-code-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.discountCode.createdAt')}
                id="discount-code-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="discount-code-order"
                name="order"
                data-cy="order"
                label={translate('ianthaApp.discountCode.order')}
                type="select"
              >
                <option value="" key="0" />
                {orders
                  ? orders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/discount-code" replace color="info">
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

export default DiscountCodeUpdate;
