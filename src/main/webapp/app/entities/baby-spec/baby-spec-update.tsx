import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IBabySpec } from 'app/shared/model/baby-spec.model';
import { ProdStatus } from 'app/shared/model/enumerations/prod-status.model';
import { getEntity, updateEntity, createEntity, reset } from './baby-spec.reducer';

export const BabySpecUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const babySpecEntity = useAppSelector(state => state.babySpec.entity);
  const loading = useAppSelector(state => state.babySpec.loading);
  const updating = useAppSelector(state => state.babySpec.updating);
  const updateSuccess = useAppSelector(state => state.babySpec.updateSuccess);
  const prodStatusValues = Object.keys(ProdStatus);

  const handleClose = () => {
    navigate('/baby-spec');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProducts({}));
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
    if (values.specQuantity !== undefined && typeof values.specQuantity !== 'number') {
      values.specQuantity = Number(values.specQuantity);
    }
    if (values.guidePrice !== undefined && typeof values.guidePrice !== 'number') {
      values.guidePrice = Number(values.guidePrice);
    }
    if (values.specPrice !== undefined && typeof values.specPrice !== 'number') {
      values.specPrice = Number(values.specPrice);
    }
    if (values.showPrice !== undefined && typeof values.showPrice !== 'number') {
      values.showPrice = Number(values.showPrice);
    }
    if (values.sellCount !== undefined && typeof values.sellCount !== 'number') {
      values.sellCount = Number(values.sellCount);
    }
    if (values.stockCount !== undefined && typeof values.stockCount !== 'number') {
      values.stockCount = Number(values.stockCount);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    const entity = {
      ...babySpecEntity,
      ...values,
      products: products.find(it => it.id.toString() === values.products?.toString()),
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
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
        }
      : {
          specStatus: 'PRE',
          ...babySpecEntity,
          createdAt: convertDateTimeFromServer(babySpecEntity.createdAt),
          updatedAt: convertDateTimeFromServer(babySpecEntity.updatedAt),
          products: babySpecEntity?.products?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ianthaApp.babySpec.home.createOrEditLabel" data-cy="BabySpecCreateUpdateHeading">
            <Translate contentKey="ianthaApp.babySpec.home.createOrEditLabel">Create or edit a BabySpec</Translate>
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
                  id="baby-spec-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ianthaApp.babySpec.specCode')}
                id="baby-spec-specCode"
                name="specCode"
                data-cy="specCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="specCodeLabel">
                <Translate contentKey="ianthaApp.babySpec.help.specCode" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.specTitle')}
                id="baby-spec-specTitle"
                name="specTitle"
                data-cy="specTitle"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="specTitleLabel">
                <Translate contentKey="ianthaApp.babySpec.help.specTitle" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.description')}
                id="baby-spec-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="descriptionLabel">
                <Translate contentKey="ianthaApp.babySpec.help.description" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.specQuantity')}
                id="baby-spec-specQuantity"
                name="specQuantity"
                data-cy="specQuantity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="specQuantityLabel">
                <Translate contentKey="ianthaApp.babySpec.help.specQuantity" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.guidePrice')}
                id="baby-spec-guidePrice"
                name="guidePrice"
                data-cy="guidePrice"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="guidePriceLabel">
                <Translate contentKey="ianthaApp.babySpec.help.guidePrice" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.specPrice')}
                id="baby-spec-specPrice"
                name="specPrice"
                data-cy="specPrice"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="specPriceLabel">
                <Translate contentKey="ianthaApp.babySpec.help.specPrice" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.showPrice')}
                id="baby-spec-showPrice"
                name="showPrice"
                data-cy="showPrice"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="showPriceLabel">
                <Translate contentKey="ianthaApp.babySpec.help.showPrice" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.specStatus')}
                id="baby-spec-specStatus"
                name="specStatus"
                data-cy="specStatus"
                type="select"
              >
                {prodStatusValues.map(prodStatus => (
                  <option value={prodStatus} key={prodStatus}>
                    {translate('ianthaApp.ProdStatus.' + prodStatus)}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="specStatusLabel">
                <Translate contentKey="ianthaApp.babySpec.help.specStatus" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.images')}
                id="baby-spec-images"
                name="images"
                data-cy="images"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="imagesLabel">
                <Translate contentKey="ianthaApp.babySpec.help.images" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.sellCount')}
                id="baby-spec-sellCount"
                name="sellCount"
                data-cy="sellCount"
                type="text"
              />
              <UncontrolledTooltip target="sellCountLabel">
                <Translate contentKey="ianthaApp.babySpec.help.sellCount" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.stockCount')}
                id="baby-spec-stockCount"
                name="stockCount"
                data-cy="stockCount"
                type="text"
              />
              <UncontrolledTooltip target="stockCountLabel">
                <Translate contentKey="ianthaApp.babySpec.help.stockCount" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babySpec.createdAt')}
                id="baby-spec-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.babySpec.updatedAt')}
                id="baby-spec-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="baby-spec-products"
                name="products"
                data-cy="products"
                label={translate('ianthaApp.babySpec.products')}
                type="select"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/baby-spec" replace color="info">
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

export default BabySpecUpdate;
