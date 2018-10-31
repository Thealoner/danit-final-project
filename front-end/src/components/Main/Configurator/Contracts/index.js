import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';

class Contracts extends Component {
  render () {
    return (
      <div className="configurator__links-wrapper">
        <NavLink exact to="/configurator/contracts/contract1" activeClassName="configurator__link--active" className="configurator__link">Контракт 1</NavLink>
        <NavLink exact to="/configurator/contracts/contract2" activeClassName="configurator__link--active" className="configurator__link">Контракт 2</NavLink>
        <NavLink exact to="/configurator/contracts/contract3" activeClassName="configurator__link--active" className="configurator__link">Контракт 3</NavLink>
      </div>
    );
  }
}

export default Contracts;