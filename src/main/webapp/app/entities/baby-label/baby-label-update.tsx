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
import { IBabyLabel } from 'app/shared/model/baby-label.model';
import { LabelCate } from 'app/shared/model/enumerations/label-cate.model';
import { getEntity, updateEntity, createEntity, reset } from './baby-label.reducer';

export const BabyLabelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const babyLabelEntity = useAppSelector(state => state.babyLabel.entity);
  const loading = useAppSelector(state => state.babyLabel.loading);
  const updating = useAppSelector(state => state.babyLabel.updating);
  const updateSuccess = useAppSelector(state => state.babyLabel.updateSuccess);
  const labelCateValues = Object.keys(LabelCate);

  const handleClose = () => {
    navigate('/baby-label');
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

    const entity = {
      ...babyLabelEntity,
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
      ? {}
      : {
          labelCate: 'SEARCH',
          ...babyLabelEntity,
          products: babyLabelEntity?.products?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ianthaApp.babyLabel.home.createOrEditLabel" data-cy="BabyLabelCreateUpdateHeading">
            <Translate contentKey="ianthaApp.babyLabel.home.createOrEditLabel">Create or edit a BabyLabel</Translate>
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
                  id="baby-label-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ianthaApp.babyLabel.title')}
                id="baby-label-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="titleLabel">
                <Translate contentKey="ianthaApp.babyLabel.help.title" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babyLabel.labelCate')}
                id="baby-label-labelCate"
                name="labelCate"
                data-cy="labelCate"
                type="select"
              >
                {labelCateValues.map(labelCate => (
                  <option value={labelCate} key={labelCate}>
                    {translate('ianthaApp.LabelCate.' + labelCate)}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="labelCateLabel">
                <Translate contentKey="ianthaApp.babyLabel.help.labelCate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babyLabel.labelCode')}
                id="baby-label-labelCode"
                name="labelCode"
                data-cy="labelCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="labelCodeLabel">
                <Translate contentKey="ianthaApp.babyLabel.help.labelCode" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babyLabel.labelAttr')}
                id="baby-label-labelAttr"
                name="labelAttr"
                data-cy="labelAttr"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="labelAttrLabel">
                <Translate contentKey="ianthaApp.babyLabel.help.labelAttr" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babyLabel.identify')}
                id="baby-label-identify"
                name="identify"
                data-cy="identify"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="identifyLabel">
                <Translate contentKey="ianthaApp.babyLabel.help.identify" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babyLabel.ruleReadme')}
                id="baby-label-ruleReadme"
                name="ruleReadme"
                data-cy="ruleReadme"
                type="textarea"
              />
              <UncontrolledTooltip target="ruleReadmeLabel">
                <Translate contentKey="ianthaApp.babyLabel.help.ruleReadme" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.babyLabel.ruleExpression')}
                id="baby-label-ruleExpression"
                name="ruleExpression"
                data-cy="ruleExpression"
                type="textarea"
              />
              <UncontrolledTooltip target="ruleExpressionLabel">
                <Translate contentKey="ianthaApp.babyLabel.help.ruleExpression" />
              </UncontrolledTooltip>
              <ValidatedField
                id="baby-label-products"
                name="products"
                data-cy="products"
                label={translate('ianthaApp.babyLabel.products')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/baby-label" replace color="info">
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

export default BabyLabelUpdate;
