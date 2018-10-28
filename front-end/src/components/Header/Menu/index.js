import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './index.scss';

class Menu extends Component {
  render () {
    return (
      <div className="menu">
        <Link to="/" className="menu__link">Главная</Link>
        <Link to="/configurator" className="menu__link">Конфигуратор</Link>
      </div>
    );
  }
}

export default Menu;