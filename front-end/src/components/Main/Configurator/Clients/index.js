import React, {Component} from 'react';
import './index.scss';
import {NavLink} from 'react-router-dom';

class Clients extends Component {
  render () {
    return (
      <div className="configurator__links-wrapper">
        <NavLink exact to="/configurator/clients/category1" activeClassName="configurator__link--active" className="configurator__link">Категория 1</NavLink>
        <NavLink exact to="/configurator/clients/category2" activeClassName="configurator__link--active" className="configurator__link">Категория 2</NavLink>
        <NavLink exact to="/configurator/clients/category3" activeClassName="configurator__link--active" className="configurator__link">Категория 3</NavLink>
        <NavLink exact to="/configurator/clients/category4" activeClassName="configurator__link--active" className="configurator__link">Категория 4</NavLink>
        <NavLink exact to="/configurator/clients/category5" activeClassName="configurator__link--active" className="configurator__link">Категория 5</NavLink>
      </div>
    );
  }
}

export default Clients;