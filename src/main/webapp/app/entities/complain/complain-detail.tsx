import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './complain.reducer';
import { IComplain } from 'app/shared/model/complain.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IComplainDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ComplainDetail extends React.Component<IComplainDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { complainEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Complain [<b>{complainEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="details">Details</span>
            </dt>
            <dd>{complainEntity.details}</dd>
            <dt>
              <span id="img">Img</span>
            </dt>
            <dd>
              {complainEntity.img ? (
                <div>
                  <a onClick={openFile(complainEntity.imgContentType, complainEntity.img)}>
                    <img src={`data:${complainEntity.imgContentType};base64,${complainEntity.img}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {complainEntity.imgContentType}, {byteSize(complainEntity.img)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="expection">Expection</span>
            </dt>
            <dd>{complainEntity.expection}</dd>
            <dt>
              <span id="showAnonymous">Show Anonymous</span>
            </dt>
            <dd>{complainEntity.showAnonymous ? 'true' : 'false'}</dd>
            <dt>
              <span id="escalate">Escalate</span>
            </dt>
            <dd>{complainEntity.escalate}</dd>
            <dt>
              <span id="resolution">Resolution</span>
            </dt>
            <dd>{complainEntity.resolution}</dd>
            <dt>Department</dt>
            <dd>{complainEntity.department ? complainEntity.department.id : ''}</dd>
            <dt>Status</dt>
            <dd>{complainEntity.status ? complainEntity.status.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/complain" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/complain/${complainEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ complain }: IRootState) => ({
  complainEntity: complain.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ComplainDetail);
