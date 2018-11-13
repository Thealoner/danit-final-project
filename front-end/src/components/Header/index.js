import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './index.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { FormattedMessage } from 'react-intl';

class Header extends Component {
  render () {
    const {handleLogout, userName} = this.props;

    return (
      <div className="header">
        <Link to="/" className="header__logo">LOGO</Link>
        <div className="header__user">
          <FontAwesomeIcon className="header__avatar" icon="user-circle" size="2x" title={userName}/>
          <button type="button" className="header__btn-logout" onClick={handleLogout}>
            <FormattedMessage id="header_logout" defaultMessage="Logout"/>
          </button>
        </div>
      </div>
    );
  }
}

export default Header;