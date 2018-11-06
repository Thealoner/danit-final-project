import React, { Component } from 'react';
import { NavLink, Route } from 'react-router-dom';
import './index.scss';
import Grid from './Grid';
import GridEntities from './GridEntities';

class Manager extends Component {
    state = {
      links: []
    };

    constructor (props) {
      super(props);

      GridEntities.forEach((entity) => {
        this.state.links.push(
          <NavLink to={'/manager/' + entity.id} key={entity.id} className="configurator__link"
            activeClassName="configurator__link--active">{entity.name}</NavLink>
        );
      });
    }

    render () {
      return (
        <main className="configurator">
          <div className="configurator__left">
            {this.state.links}
          </div>
          <div className="configurator__right">
            <Route path="/manager/:entityId" component={Grid} />
          </div>
        </main>
      );
    }
}

export default Manager;