import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './baby-spec.reducer';

export const BabySpecDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const babySpecEntity = useAppSelector(state => state.babySpec.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="babySpecDetailsHeading">
          <Translate contentKey="ianthaApp.babySpec.detail.title">BabySpec</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{babySpecEntity.id}</dd>
          <dt>
            <span id="specCode">
              <Translate contentKey="ianthaApp.babySpec.specCode">Spec Code</Translate>
            </span>
            <UncontrolledTooltip target="specCode">
              <Translate contentKey="ianthaApp.babySpec.help.specCode" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.specCode}</dd>
          <dt>
            <span id="specTitle">
              <Translate contentKey="ianthaApp.babySpec.specTitle">Spec Title</Translate>
            </span>
            <UncontrolledTooltip target="specTitle">
              <Translate contentKey="ianthaApp.babySpec.help.specTitle" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.specTitle}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="ianthaApp.babySpec.description">Description</Translate>
            </span>
            <UncontrolledTooltip target="description">
              <Translate contentKey="ianthaApp.babySpec.help.description" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.description}</dd>
          <dt>
            <span id="specQuantity">
              <Translate contentKey="ianthaApp.babySpec.specQuantity">Spec Quantity</Translate>
            </span>
            <UncontrolledTooltip target="specQuantity">
              <Translate contentKey="ianthaApp.babySpec.help.specQuantity" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.specQuantity}</dd>
          <dt>
            <span id="guidePrice">
              <Translate contentKey="ianthaApp.babySpec.guidePrice">Guide Price</Translate>
            </span>
            <UncontrolledTooltip target="guidePrice">
              <Translate contentKey="ianthaApp.babySpec.help.guidePrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.guidePrice}</dd>
          <dt>
            <span id="specPrice">
              <Translate contentKey="ianthaApp.babySpec.specPrice">Spec Price</Translate>
            </span>
            <UncontrolledTooltip target="specPrice">
              <Translate contentKey="ianthaApp.babySpec.help.specPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.specPrice}</dd>
          <dt>
            <span id="showPrice">
              <Translate contentKey="ianthaApp.babySpec.showPrice">Show Price</Translate>
            </span>
            <UncontrolledTooltip target="showPrice">
              <Translate contentKey="ianthaApp.babySpec.help.showPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.showPrice}</dd>
          <dt>
            <span id="specStatus">
              <Translate contentKey="ianthaApp.babySpec.specStatus">Spec Status</Translate>
            </span>
            <UncontrolledTooltip target="specStatus">
              <Translate contentKey="ianthaApp.babySpec.help.specStatus" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.specStatus}</dd>
          <dt>
            <span id="images">
              <Translate contentKey="ianthaApp.babySpec.images">Images</Translate>
            </span>
            <UncontrolledTooltip target="images">
              <Translate contentKey="ianthaApp.babySpec.help.images" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.images}</dd>
          <dt>
            <span id="sellCount">
              <Translate contentKey="ianthaApp.babySpec.sellCount">Sell Count</Translate>
            </span>
            <UncontrolledTooltip target="sellCount">
              <Translate contentKey="ianthaApp.babySpec.help.sellCount" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.sellCount}</dd>
          <dt>
            <span id="stockCount">
              <Translate contentKey="ianthaApp.babySpec.stockCount">Stock Count</Translate>
            </span>
            <UncontrolledTooltip target="stockCount">
              <Translate contentKey="ianthaApp.babySpec.help.stockCount" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babySpecEntity.stockCount}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="ianthaApp.babySpec.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{babySpecEntity.createdAt ? <TextFormat value={babySpecEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="ianthaApp.babySpec.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{babySpecEntity.updatedAt ? <TextFormat value={babySpecEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="ianthaApp.babySpec.products">Products</Translate>
          </dt>
          <dd>{babySpecEntity.products ? babySpecEntity.products.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/baby-spec" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/baby-spec/${babySpecEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BabySpecDetail;
