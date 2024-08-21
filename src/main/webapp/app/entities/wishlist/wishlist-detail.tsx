import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wishlist.reducer';

export const WishlistDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const wishlistEntity = useAppSelector(state => state.wishlist.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wishlistDetailsHeading">
          <Translate contentKey="ianthaApp.wishlist.detail.title">Wishlist</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{wishlistEntity.id}</dd>
          <dt>
            <span id="identify">
              <Translate contentKey="ianthaApp.wishlist.identify">Identify</Translate>
            </span>
            <UncontrolledTooltip target="identify">
              <Translate contentKey="ianthaApp.wishlist.help.identify" />
            </UncontrolledTooltip>
          </dt>
          <dd>{wishlistEntity.identify}</dd>
          <dt>
            <span id="bizCode">
              <Translate contentKey="ianthaApp.wishlist.bizCode">Biz Code</Translate>
            </span>
            <UncontrolledTooltip target="bizCode">
              <Translate contentKey="ianthaApp.wishlist.help.bizCode" />
            </UncontrolledTooltip>
          </dt>
          <dd>{wishlistEntity.bizCode}</dd>
          <dt>
            <span id="bizDesc">
              <Translate contentKey="ianthaApp.wishlist.bizDesc">Biz Desc</Translate>
            </span>
            <UncontrolledTooltip target="bizDesc">
              <Translate contentKey="ianthaApp.wishlist.help.bizDesc" />
            </UncontrolledTooltip>
          </dt>
          <dd>{wishlistEntity.bizDesc}</dd>
          <dt>
            <span id="bizIcon">
              <Translate contentKey="ianthaApp.wishlist.bizIcon">Biz Icon</Translate>
            </span>
            <UncontrolledTooltip target="bizIcon">
              <Translate contentKey="ianthaApp.wishlist.help.bizIcon" />
            </UncontrolledTooltip>
          </dt>
          <dd>{wishlistEntity.bizIcon}</dd>
          <dt>
            <span id="bizTitle">
              <Translate contentKey="ianthaApp.wishlist.bizTitle">Biz Title</Translate>
            </span>
            <UncontrolledTooltip target="bizTitle">
              <Translate contentKey="ianthaApp.wishlist.help.bizTitle" />
            </UncontrolledTooltip>
          </dt>
          <dd>{wishlistEntity.bizTitle}</dd>
          <dt>
            <span id="link">
              <Translate contentKey="ianthaApp.wishlist.link">Link</Translate>
            </span>
            <UncontrolledTooltip target="link">
              <Translate contentKey="ianthaApp.wishlist.help.link" />
            </UncontrolledTooltip>
          </dt>
          <dd>{wishlistEntity.link}</dd>
          <dt>
            <span id="favCate">
              <Translate contentKey="ianthaApp.wishlist.favCate">Fav Cate</Translate>
            </span>
            <UncontrolledTooltip target="favCate">
              <Translate contentKey="ianthaApp.wishlist.help.favCate" />
            </UncontrolledTooltip>
          </dt>
          <dd>{wishlistEntity.favCate}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="ianthaApp.wishlist.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {wishlistEntity.createdDate ? <TextFormat value={wishlistEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/wishlist" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wishlist/${wishlistEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WishlistDetail;
