import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './branch.reducer';
import { IBranch } from 'app/shared/model/branch.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBranchDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BranchDetail extends React.Component<IBranchDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { branchEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Branch [<b>{branchEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="branchName">Branch Name</span>
            </dt>
            <dd>{branchEntity.branchName}</dd>
            <dt>
              <span id="phone">Phone</span>
            </dt>
            <dd>{branchEntity.phone}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{branchEntity.email}</dd>
            <dt>Location</dt>
            <dd>{branchEntity.location ? branchEntity.location.id : ''}</dd>
            <dt>Company</dt>
            <dd>{branchEntity.company ? branchEntity.company.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/branch" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/branch/${branchEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ branch }: IRootState) => ({
  branchEntity: branch.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BranchDetail);
