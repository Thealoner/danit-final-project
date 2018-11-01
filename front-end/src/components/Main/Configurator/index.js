import React, { Component } from 'react';
import { NavLink, Route } from 'react-router-dom';
import './index.scss';
import Grid from './Grid';
import GridEntities from './GridEntities';

class Body extends Component {
  state = {
    links: []
  };

  constructor (props) {
    super(props);

    GridEntities.forEach((entity) => {
      this.state.links.push(
        <li key={entity.id} className="configurator__menuitem">
          <NavLink exact to={'/configurator/' + entity.id} activeClassName="menu__link--active" className="menu__link">{entity.name}</NavLink>
        </li>
      );
    });
  }

  render () {
    return (
      <div className="configurator">
        <div className="configurator__left">
          <ul className="configurator__menu">
            {this.state.links}
          </ul>
        </div>
        <div className="configurator__right">
          <Route path="/configurator/:entityId" component={Grid} />
        </div>
      </div>
    );
  }
}

export default Body;