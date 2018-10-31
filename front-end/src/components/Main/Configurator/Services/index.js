import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';

class Services extends Component {
  render () {
    return (
      <div className="configurator__links-wrapper">
        <NavLink exact to="/configurator/services/service1" activeClassName="configurator__link--active" className="configurator__link">Сервис 1</NavLink>
        <NavLink exact to="/configurator/services/service2" activeClassName="configurator__link--active" className="configurator__link">Сервис 2</NavLink>
        <NavLink exact to="/configurator/services/service3" activeClassName="configurator__link--active" className="configurator__link">Сервис 3</NavLink>
      </div>
    );
  }
}

export default Services;