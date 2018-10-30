import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';
import './index.scss';

class Menu extends Component {
  render () {
    return (
      <div className="menu">
        <NavLink exact to="/" activeClassName="menu__link--active" className="menu__link">Главная</NavLink>
        <NavLink exact to="/configurator" activeClassName="menu__link--active" className="menu__link">Конфигуратор</NavLink>
      </div>
    );
  }
}

export default Menu;