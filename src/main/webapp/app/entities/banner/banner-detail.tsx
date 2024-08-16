import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './banner.reducer';

export const BannerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bannerEntity = useAppSelector(state => state.banner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bannerDetailsHeading">
          <Translate contentKey="ianthaApp.banner.detail.title">Banner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.id}</dd>
          <dt>
            <span id="label">
              <Translate contentKey="ianthaApp.banner.label">Label</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.label}</dd>
          <dt>
            <span id="images">
              <Translate contentKey="ianthaApp.banner.images">Images</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.images}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="ianthaApp.banner.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.createdAt ? <TextFormat value={bannerEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="ianthaApp.banner.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.updatedAt ? <TextFormat value={bannerEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/banner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/banner/${bannerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BannerDetail;
