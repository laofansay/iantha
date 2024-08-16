import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './baby-label.reducer';

export const BabyLabelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const babyLabelEntity = useAppSelector(state => state.babyLabel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="babyLabelDetailsHeading">
          <Translate contentKey="ianthaApp.babyLabel.detail.title">BabyLabel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{babyLabelEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="ianthaApp.babyLabel.title">Title</Translate>
            </span>
            <UncontrolledTooltip target="title">
              <Translate contentKey="ianthaApp.babyLabel.help.title" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babyLabelEntity.title}</dd>
          <dt>
            <span id="labelCate">
              <Translate contentKey="ianthaApp.babyLabel.labelCate">Label Cate</Translate>
            </span>
            <UncontrolledTooltip target="labelCate">
              <Translate contentKey="ianthaApp.babyLabel.help.labelCate" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babyLabelEntity.labelCate}</dd>
          <dt>
            <span id="labelCode">
              <Translate contentKey="ianthaApp.babyLabel.labelCode">Label Code</Translate>
            </span>
            <UncontrolledTooltip target="labelCode">
              <Translate contentKey="ianthaApp.babyLabel.help.labelCode" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babyLabelEntity.labelCode}</dd>
          <dt>
            <span id="labelAttr">
              <Translate contentKey="ianthaApp.babyLabel.labelAttr">Label Attr</Translate>
            </span>
            <UncontrolledTooltip target="labelAttr">
              <Translate contentKey="ianthaApp.babyLabel.help.labelAttr" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babyLabelEntity.labelAttr}</dd>
          <dt>
            <span id="identify">
              <Translate contentKey="ianthaApp.babyLabel.identify">Identify</Translate>
            </span>
            <UncontrolledTooltip target="identify">
              <Translate contentKey="ianthaApp.babyLabel.help.identify" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babyLabelEntity.identify}</dd>
          <dt>
            <span id="ruleReadme">
              <Translate contentKey="ianthaApp.babyLabel.ruleReadme">Rule Readme</Translate>
            </span>
            <UncontrolledTooltip target="ruleReadme">
              <Translate contentKey="ianthaApp.babyLabel.help.ruleReadme" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babyLabelEntity.ruleReadme}</dd>
          <dt>
            <span id="ruleExpression">
              <Translate contentKey="ianthaApp.babyLabel.ruleExpression">Rule Expression</Translate>
            </span>
            <UncontrolledTooltip target="ruleExpression">
              <Translate contentKey="ianthaApp.babyLabel.help.ruleExpression" />
            </UncontrolledTooltip>
          </dt>
          <dd>{babyLabelEntity.ruleExpression}</dd>
          <dt>
            <Translate contentKey="ianthaApp.babyLabel.products">Products</Translate>
          </dt>
          <dd>{babyLabelEntity.products ? babyLabelEntity.products.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/baby-label" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/baby-label/${babyLabelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BabyLabelDetail;
