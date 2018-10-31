import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';

class Organizations extends Component {
  render () {
    return (
      <div className="configurator__links-wrapper">
        <NavLink exact to="/configurator/organizations/organization1" activeClassName="configurator__link--active" className="configurator__link">Огранизация 1</NavLink>
        <NavLink exact to="/configurator/organizations/organization2" activeClassName="configurator__link--active" className="configurator__link">Огранизация 2</NavLink>
      </div>
    );
  }
}

export default Organizations;