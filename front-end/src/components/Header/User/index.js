import React, {Component} from 'react';
import './index.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {ButtonToolbar, Dropdown, MenuItem} from 'react-bootstrap';

class User extends Component {
  render () {
    const {handleLogout, userName} = this.props;

    return (
      <div className="user">

        <ButtonToolbar>
          <Dropdown id="dropdown-custom-1" pullRight={true}>
            <Dropdown.Toggle>
              <FontAwesomeIcon className="user__avatar" icon="user-circle" size="2x" title={userName}/>
            </Dropdown.Toggle>
            <Dropdown.Menu className="super-colors" >
              <MenuItem eventKey="1" disabled={true}>
                {userName}
              </MenuItem>
              <MenuItem divider />
              <MenuItem eventKey="2" onClick={handleLogout}>
               Выйти
              </MenuItem>
            </Dropdown.Menu>
          </Dropdown>

        </ButtonToolbar>
      </div>

    );
  }
}

export default User;