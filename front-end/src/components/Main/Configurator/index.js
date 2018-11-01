import React, { Component } from 'react';
import { NavLink } from 'react-router-dom';
import './index.scss';
import Packages from './Packages';
import samplePackages from './SampleJson/packages.json';

class Body extends Component {
  render() {
    return (
      <div className="configurator">
        <div className="configurator__left">
          <ul className="configurator__menu">
            <li className="configurator__menuitem"><NavLink exact to="/configurator/packages" activeClassName="menu__link--active" className="menu__link">Пакеты</NavLink></li>
            <li className="configurator__menuitem"><NavLink exact to="/configurator/clients" activeClassName="menu__link--active" className="menu__link">Клиенты</NavLink></li>
          </ul>
        </div>
        <div className="configurator__right">
          <Packages data={samplePackages} />
        </div>
      </div>
    );
  }
}

export default Body;