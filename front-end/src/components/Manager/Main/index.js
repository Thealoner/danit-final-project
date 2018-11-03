import React, { Component } from 'react';
import { NavLink, Route } from 'react-router-dom';
import './index.scss';
import Grid from './Grid';
import GridEntities from './GridEntities';

class Main extends Component {
    state = {
      links: []
    };

    constructor (props) {
      super(props);

      GridEntities.forEach((entity) => {
        this.state.links.push(
          <li key={entity.id} className="main__menuitem">
            <NavLink exact to={'/manager/' + entity.id} activeClassName="menu__link--active" className="menu__link">{entity.name}</NavLink>
          </li>
        );
      });
    }

    render () {
      return (
        <main className="main">
          <div className="main__left">
            <ul className="main__menu">
              {this.state.links}
            </ul>
          </div>
          <div className="main__right">
            <Route path="/manager/:entityId" component={Grid} />
          </div>
        </main>
      );
    }
}

export default Main;