import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWishlist } from 'app/shared/model/wishlist.model';
import { FavCate } from 'app/shared/model/enumerations/fav-cate.model';
import { getEntity, updateEntity, createEntity, reset } from './wishlist.reducer';

export const WishlistUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const wishlistEntity = useAppSelector(state => state.wishlist.entity);
  const loading = useAppSelector(state => state.wishlist.loading);
  const updating = useAppSelector(state => state.wishlist.updating);
  const updateSuccess = useAppSelector(state => state.wishlist.updateSuccess);
  const favCateValues = Object.keys(FavCate);

  const handleClose = () => {
    navigate('/wishlist');
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
    values.createdDate = convertDateTimeToServer(values.createdDate);

    const entity = {
      ...wishlistEntity,
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
          createdDate: displayDefaultDateTime(),
        }
      : {
          favCate: 'PROD',
          ...wishlistEntity,
          createdDate: convertDateTimeFromServer(wishlistEntity.createdDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ianthaApp.wishlist.home.createOrEditLabel" data-cy="WishlistCreateUpdateHeading">
            <Translate contentKey="ianthaApp.wishlist.home.createOrEditLabel">Create or edit a Wishlist</Translate>
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
                  id="wishlist-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ianthaApp.wishlist.identify')}
                id="wishlist-identify"
                name="identify"
                data-cy="identify"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="identifyLabel">
                <Translate contentKey="ianthaApp.wishlist.help.identify" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.wishlist.bizCode')}
                id="wishlist-bizCode"
                name="bizCode"
                data-cy="bizCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="bizCodeLabel">
                <Translate contentKey="ianthaApp.wishlist.help.bizCode" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.wishlist.bizDesc')}
                id="wishlist-bizDesc"
                name="bizDesc"
                data-cy="bizDesc"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="bizDescLabel">
                <Translate contentKey="ianthaApp.wishlist.help.bizDesc" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.wishlist.bizIcon')}
                id="wishlist-bizIcon"
                name="bizIcon"
                data-cy="bizIcon"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="bizIconLabel">
                <Translate contentKey="ianthaApp.wishlist.help.bizIcon" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.wishlist.bizTitle')}
                id="wishlist-bizTitle"
                name="bizTitle"
                data-cy="bizTitle"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="bizTitleLabel">
                <Translate contentKey="ianthaApp.wishlist.help.bizTitle" />
              </UncontrolledTooltip>
              <ValidatedField label={translate('ianthaApp.wishlist.link')} id="wishlist-link" name="link" data-cy="link" type="text" />
              <UncontrolledTooltip target="linkLabel">
                <Translate contentKey="ianthaApp.wishlist.help.link" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.wishlist.favCate')}
                id="wishlist-favCate"
                name="favCate"
                data-cy="favCate"
                type="select"
              >
                {favCateValues.map(favCate => (
                  <option value={favCate} key={favCate}>
                    {translate('ianthaApp.FavCate.' + favCate)}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="favCateLabel">
                <Translate contentKey="ianthaApp.wishlist.help.favCate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('ianthaApp.wishlist.createdDate')}
                id="wishlist-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/wishlist" replace color="info">
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

export default WishlistUpdate;
