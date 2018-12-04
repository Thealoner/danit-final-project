import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './index.scss';
import User from './User';
import logo from './EG-logo.svg';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

class Header extends Component {
  render () {
    const {handleLogout, userName} = this.props;
    return <div className="header">
      <div className="header__left-col">
        <Link to="/" className="header__logo-link">
          <img className="header__logo" src={logo} alt="logo"/>
        </Link>
      </div>
      <div className="header__right-col">
        <button className="header__addClient">
          <FontAwesomeIcon className="header__plus-icon" icon="plus" size="1x"/>
        Новый клиент</button>
        <User handleLogout={handleLogout} userName={userName}/>
      </div>

    </div>;
  }
}

export default Header;