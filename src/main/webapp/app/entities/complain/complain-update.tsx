import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDepartment } from 'app/shared/model/department.model';
import { getEntities as getDepartments } from 'app/entities/department/department.reducer';
import { IStatus } from 'app/shared/model/status.model';
import { getEntities as getStatuses } from 'app/entities/status/status.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './complain.reducer';
import { IComplain } from 'app/shared/model/complain.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IComplainUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IComplainUpdateState {
  isNew: boolean;
  departmentId: string;
  statusId: string;
}

export class ComplainUpdate extends React.Component<IComplainUpdateProps, IComplainUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      departmentId: '0',
      statusId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getDepartments();
    this.props.getStatuses();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { complainEntity } = this.props;
      const entity = {
        ...complainEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/complain');
  };

  render() {
    const { complainEntity, departments, statuses, loading, updating } = this.props;
    const { isNew } = this.state;

    const { img, imgContentType } = complainEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="complainportalApp.complain.home.createOrEditLabel">Create or edit a Complain</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : complainEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="complain-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="detailsLabel" for="details">
                    Details
                  </Label>
                  <AvField id="complain-details" type="text" name="details" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imgLabel" for="img">
                      Img
                    </Label>
                    <br />
                    {img ? (
                      <div>
                        <a onClick={openFile(imgContentType, img)}>
                          <img src={`data:${imgContentType};base64,${img}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {imgContentType}, {byteSize(img)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('img')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_img" type="file" onChange={this.onBlobChange(true, 'img')} accept="image/*" />
                    <AvInput type="hidden" name="img" value={img} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="expectionLabel" for="expection">
                    Expection
                  </Label>
                  <AvField id="complain-expection" type="text" name="expection" />
                </AvGroup>
                <AvGroup>
                  <Label id="showAnonymousLabel" check>
                    <AvInput id="complain-showAnonymous" type="checkbox" className="form-control" name="showAnonymous" />
                    Show Anonymous
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="escalateLabel" for="escalate">
                    Escalate
                  </Label>
                  <AvField id="complain-escalate" type="text" name="escalate" />
                </AvGroup>
                <AvGroup>
                  <Label id="resolutionLabel" for="resolution">
                    Resolution
                  </Label>
                  <AvField id="complain-resolution" type="text" name="resolution" />
                </AvGroup>
                <AvGroup>
                  <Label for="department.id">Department</Label>
                  <AvInput id="complain-department" type="select" className="form-control" name="department.id">
                    <option value="" key="0" />
                    {departments
                      ? departments.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="status.id">Status</Label>
                  <AvInput id="complain-status" type="select" className="form-control" name="status.id">
                    <option value="" key="0" />
                    {statuses
                      ? statuses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/complain" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  departments: storeState.department.entities,
  statuses: storeState.status.entities,
  complainEntity: storeState.complain.entity,
  loading: storeState.complain.loading,
  updating: storeState.complain.updating,
  updateSuccess: storeState.complain.updateSuccess
});

const mapDispatchToProps = {
  getDepartments,
  getStatuses,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ComplainUpdate);
