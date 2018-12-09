import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './index.scss';
import User from './User';
import logo from './EG-logo.svg';

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
        <User handleLogout={handleLogout} userName={userName}/>
      </div>

    </div>;
  }
}

export default Header;