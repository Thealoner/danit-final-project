import React, {Component} from 'react';
import './index.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

class User extends Component {
  render () {
    const {handleLogout, userName} = this.props;

    return (
      <div className="user">
        <FontAwesomeIcon className="user__avatar" icon="user-circle" size="2x" title={userName}/>
        <button type="button" className="user__btn-logout" onClick={handleLogout}>Выйти</button>
      </div>
    );
  }
}

export default User;