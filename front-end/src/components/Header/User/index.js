import React, {Component} from 'react';
import './index.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { FormattedMessage } from 'react-intl';
import Locale from '../Locale';

class User extends Component {
  render () {
    const {handleLogout, userName} = this.props;

    return (
      <div className="user">
        <FontAwesomeIcon className="user__avatar" icon="user-circle" size="2x" title={userName}/>
        <div className="user__wrapper">
          <Locale/>
          <button type="button" className="user__btn-logout" onClick={handleLogout}>
            <FormattedMessage id="user_logout" defaultMessage="Выйти"/>
          </button>
        </div>
      </div>
    );
  }
}

export default User;