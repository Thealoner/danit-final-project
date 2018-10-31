import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';

class Packages extends Component {
  render () {
    return (
      <div className="configurator__links-wrapper">
        <NavLink exact to="/configurator/packages/package1" activeClassName="configurator__link--active" className="configurator__link">Пакет 1</NavLink>
        <NavLink exact to="/configurator/packages/package2" activeClassName="configurator__link--active" className="configurator__link">Пакет 2</NavLink>
        <NavLink exact to="/configurator/packages/package3" activeClassName="configurator__link--active" className="configurator__link">Пакет 3</NavLink>
        <NavLink exact to="/configurator/packages/package4" activeClassName="configurator__link--active" className="configurator__link">Пакет 4</NavLink>
        <NavLink exact to="/configurator/packages/package5" activeClassName="configurator__link--active" className="configurator__link">Пакет 5</NavLink>
      </div>
    );
  }
}

export default Packages;