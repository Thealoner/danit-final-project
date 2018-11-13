import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './index.scss';
import User from './User';

class Header extends Component {
  render () {
    const {handleLogout, userName} = this.props;
    return (
      <div className="header">
        <Link to="/" className="header__logo">LOGO</Link>
        <User handleLogout={handleLogout} userName={userName}/>
      </div>
    );
  }
}

export default Header;