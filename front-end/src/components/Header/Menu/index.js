import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';
import './index.scss';

class Menu extends Component {
  render () {
    return (
      <div className="menu">
        <NavLink exact to="/" activeClassName="menu__link--active" className="menu__link">Главная</NavLink>
        <NavLink to="/configurator/packages" activeClassName="menu__link--active" className="menu__link">Пакеты</NavLink>
        <NavLink to="/configurator/clients" activeClassName="menu__link--active" className="menu__link">Клиенты</NavLink>
        <NavLink to="/configurator/contracts" activeClassName="menu__link--active" className="menu__link">Контракты</NavLink>
        <NavLink to="/configurator/services" activeClassName="menu__link--active" className="menu__link">Сервисы</NavLink>
        <NavLink to="/configurator/organizations" activeClassName="menu__link--active" className="menu__link">Организации</NavLink>
      </div>
    );
  }
}

export default Menu;