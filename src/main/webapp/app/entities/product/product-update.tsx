import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProduct } from 'app/shared/model/product.model';
import { ProdStatus } from 'app/shared/model/enumerations/prod-status.model';
import { getEntity, updateEntity, createEntity, reset } from './product.reducer';

export const ProductUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const productEntity = useAppSelector(state => state.product.entity);
  const loading = useAppSelector(state => state.product.loading);
  const updating = useAppSelector(state => state.product.updating);
  const updateSuccess = useAppSelector(state => state.product.updateSuccess);
  const prodStatusValues = Object.keys(ProdStatus);

  const handleClose = () => {
    navigate('/product');
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
    if (values.guidePrice !== undefined && typeof values.guidePrice !== 'number') {
      values.guidePrice = Number(values.guidePrice);
    }
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }
    if (values.showPrice !== undefined && typeof values.showPrice !== 'number') {
      values.showPrice = Number(values.showPrice);
    }
    if (values.discount !== undefined && typeof values.discount !== 'number') {
      values.discount = Number(values.discount);
    }
    if (values.stock !== undefined && typeof values.stock !== 'number') {
      values.stock = Number(values.stock);
    }
    if (values.sellCount !== undefined && typeof values.sellCount !== 'number') {
      values.sellCount = Number(values.sellCount);
    }
    if (values.stockCount !== undefined && typeof values.stockCount !== 'number') {
      values.stockCount = Number(values.stockCount);
    }
    values.shelvesDate = convertDateTimeToServer(values.shelvesDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    const entity = {
      ...productEntity,
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
      ? {
          shelvesDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
        }
      : {
          status: 'PRE',
          ...productEntity,
          shelvesDate: convertDateTimeFromServer(productEntity.shelvesDate),
          createdAt: convertDateTimeFromServer(productEntity.createdAt),
          updatedAt: convertDateTimeFromServer(productEntity.updatedAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ianthaApp.product.home.createOrEditLabel" data-cy="ProductCreateUpdateHeading">
            <Translate contentKey="ianthaApp.product.home.createOrEditLabel">Create or edit a Product</Translate>
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
                  id="product-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ianthaApp.product.title')}
                id="product-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.product.transCode')}
                id="product-transCode"
                name="transCode"
                data-cy="transCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="transCodeLabel">
                <Translate contentKey="ianthaApp.product.help.transCode" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.description')}
                id="product-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('ianthaApp.product.images')}
                id="product-images"
                name="images"
                data-cy="images"
                type="text"
              />
              <UncontrolledTooltip target="imagesLabel">
                <Translate contentKey="ianthaApp.product.help.images" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.keywords')}
                id="product-keywords"
                name="keywords"
                data-cy="keywords"
                type="text"
              />
              <ValidatedField
                label={translate('ianthaApp.product.metadata')}
                id="product-metadata"
                name="metadata"
                data-cy="metadata"
                type="textarea"
              />
              <ValidatedField
                label={translate('ianthaApp.product.guidePrice')}
                id="product-guidePrice"
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
                <Translate contentKey="ianthaApp.product.help.guidePrice" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.price')}
                id="product-price"
                name="price"
                data-cy="price"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="priceLabel">
                <Translate contentKey="ianthaApp.product.help.price" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.showPrice')}
                id="product-showPrice"
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
                <Translate contentKey="ianthaApp.product.help.showPrice" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.discount')}
                id="product-discount"
                name="discount"
                data-cy="discount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.product.stock')}
                id="product-stock"
                name="stock"
                data-cy="stock"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="stockLabel">
                <Translate contentKey="ianthaApp.product.help.stock" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.isPhysical')}
                id="product-isPhysical"
                name="isPhysical"
                data-cy="isPhysical"
                check
                type="checkbox"
              />
              <UncontrolledTooltip target="isPhysicalLabel">
                <Translate contentKey="ianthaApp.product.help.isPhysical" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.isAvailable')}
                id="product-isAvailable"
                name="isAvailable"
                data-cy="isAvailable"
                check
                type="checkbox"
              />
              <UncontrolledTooltip target="isAvailableLabel">
                <Translate contentKey="ianthaApp.product.help.isAvailable" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.isFeatured')}
                id="product-isFeatured"
                name="isFeatured"
                data-cy="isFeatured"
                check
                type="checkbox"
              />
              <UncontrolledTooltip target="isFeaturedLabel">
                <Translate contentKey="ianthaApp.product.help.isFeatured" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.status')}
                id="product-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {prodStatusValues.map(prodStatus => (
                  <option value={prodStatus} key={prodStatus}>
                    {translate('ianthaApp.ProdStatus.' + prodStatus)}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="statusLabel">
                <Translate contentKey="ianthaApp.product.help.status" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.sellCount')}
                id="product-sellCount"
                name="sellCount"
                data-cy="sellCount"
                type="text"
              />
              <UncontrolledTooltip target="sellCountLabel">
                <Translate contentKey="ianthaApp.product.help.sellCount" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.stockCount')}
                id="product-stockCount"
                name="stockCount"
                data-cy="stockCount"
                type="text"
              />
              <UncontrolledTooltip target="stockCountLabel">
                <Translate contentKey="ianthaApp.product.help.stockCount" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.shelvesStatus')}
                id="product-shelvesStatus"
                name="shelvesStatus"
                data-cy="shelvesStatus"
                check
                type="checkbox"
              />
              <UncontrolledTooltip target="shelvesStatusLabel">
                <Translate contentKey="ianthaApp.product.help.shelvesStatus" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.shelvesDate')}
                id="product-shelvesDate"
                name="shelvesDate"
                data-cy="shelvesDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <UncontrolledTooltip target="shelvesDateLabel">
                <Translate contentKey="ianthaApp.product.help.shelvesDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.product.createdAt')}
                id="product-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('ianthaApp.product.updatedAt')}
                id="product-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product" replace color="info">
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

export default ProductUpdate;
