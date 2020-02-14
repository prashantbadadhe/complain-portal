import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/location">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Location
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/company">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Company
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/branch">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Branch
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/department">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Department
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/status">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Status
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/category">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Category
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/complain">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Complain
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
