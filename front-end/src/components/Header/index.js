import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './index.scss';
import User from './User';

class Header extends Component {
  render () {
    return (
      <div className="header">
        <Link to="/" className="header__logo">LOGO</Link>
        <User/>
      </div>
    );
  }
}

export default Header;