import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';

export const ProductDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">
          <Translate contentKey="ianthaApp.product.detail.title">Product</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="ianthaApp.product.title">Title</Translate>
            </span>
          </dt>
          <dd>{productEntity.title}</dd>
          <dt>
            <span id="transCode">
              <Translate contentKey="ianthaApp.product.transCode">Trans Code</Translate>
            </span>
            <UncontrolledTooltip target="transCode">
              <Translate contentKey="ianthaApp.product.help.transCode" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.transCode}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="ianthaApp.product.description">Description</Translate>
            </span>
          </dt>
          <dd>{productEntity.description}</dd>
          <dt>
            <span id="images">
              <Translate contentKey="ianthaApp.product.images">Images</Translate>
            </span>
            <UncontrolledTooltip target="images">
              <Translate contentKey="ianthaApp.product.help.images" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.images}</dd>
          <dt>
            <span id="keywords">
              <Translate contentKey="ianthaApp.product.keywords">Keywords</Translate>
            </span>
          </dt>
          <dd>{productEntity.keywords}</dd>
          <dt>
            <span id="metadata">
              <Translate contentKey="ianthaApp.product.metadata">Metadata</Translate>
            </span>
          </dt>
          <dd>{productEntity.metadata}</dd>
          <dt>
            <span id="guidePrice">
              <Translate contentKey="ianthaApp.product.guidePrice">Guide Price</Translate>
            </span>
            <UncontrolledTooltip target="guidePrice">
              <Translate contentKey="ianthaApp.product.help.guidePrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.guidePrice}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="ianthaApp.product.price">Price</Translate>
            </span>
            <UncontrolledTooltip target="price">
              <Translate contentKey="ianthaApp.product.help.price" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.price}</dd>
          <dt>
            <span id="showPrice">
              <Translate contentKey="ianthaApp.product.showPrice">Show Price</Translate>
            </span>
            <UncontrolledTooltip target="showPrice">
              <Translate contentKey="ianthaApp.product.help.showPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.showPrice}</dd>
          <dt>
            <span id="discount">
              <Translate contentKey="ianthaApp.product.discount">Discount</Translate>
            </span>
          </dt>
          <dd>{productEntity.discount}</dd>
          <dt>
            <span id="stock">
              <Translate contentKey="ianthaApp.product.stock">Stock</Translate>
            </span>
            <UncontrolledTooltip target="stock">
              <Translate contentKey="ianthaApp.product.help.stock" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.stock}</dd>
          <dt>
            <span id="isPhysical">
              <Translate contentKey="ianthaApp.product.isPhysical">Is Physical</Translate>
            </span>
            <UncontrolledTooltip target="isPhysical">
              <Translate contentKey="ianthaApp.product.help.isPhysical" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.isPhysical ? 'true' : 'false'}</dd>
          <dt>
            <span id="isAvailable">
              <Translate contentKey="ianthaApp.product.isAvailable">Is Available</Translate>
            </span>
            <UncontrolledTooltip target="isAvailable">
              <Translate contentKey="ianthaApp.product.help.isAvailable" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.isAvailable ? 'true' : 'false'}</dd>
          <dt>
            <span id="isFeatured">
              <Translate contentKey="ianthaApp.product.isFeatured">Is Featured</Translate>
            </span>
            <UncontrolledTooltip target="isFeatured">
              <Translate contentKey="ianthaApp.product.help.isFeatured" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.isFeatured ? 'true' : 'false'}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="ianthaApp.product.status">Status</Translate>
            </span>
            <UncontrolledTooltip target="status">
              <Translate contentKey="ianthaApp.product.help.status" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.status}</dd>
          <dt>
            <span id="sellCount">
              <Translate contentKey="ianthaApp.product.sellCount">Sell Count</Translate>
            </span>
            <UncontrolledTooltip target="sellCount">
              <Translate contentKey="ianthaApp.product.help.sellCount" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.sellCount}</dd>
          <dt>
            <span id="stockCount">
              <Translate contentKey="ianthaApp.product.stockCount">Stock Count</Translate>
            </span>
            <UncontrolledTooltip target="stockCount">
              <Translate contentKey="ianthaApp.product.help.stockCount" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.stockCount}</dd>
          <dt>
            <span id="shelvesStatus">
              <Translate contentKey="ianthaApp.product.shelvesStatus">Shelves Status</Translate>
            </span>
            <UncontrolledTooltip target="shelvesStatus">
              <Translate contentKey="ianthaApp.product.help.shelvesStatus" />
            </UncontrolledTooltip>
          </dt>
          <dd>{productEntity.shelvesStatus ? 'true' : 'false'}</dd>
          <dt>
            <span id="shelvesDate">
              <Translate contentKey="ianthaApp.product.shelvesDate">Shelves Date</Translate>
            </span>
            <UncontrolledTooltip target="shelvesDate">
              <Translate contentKey="ianthaApp.product.help.shelvesDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {productEntity.shelvesDate ? <TextFormat value={productEntity.shelvesDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="ianthaApp.product.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{productEntity.createdAt ? <TextFormat value={productEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="ianthaApp.product.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{productEntity.updatedAt ? <TextFormat value={productEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductDetail;
