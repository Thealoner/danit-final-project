import React, { Component } from 'react';
import { NavLink } from 'react-router-dom';
import './index.scss';
import GridEntities from './GridEntities';
import Tab from './Tab';

class Main extends Component {
  state = {
    links: []
  };

  constructor (props) {
    super(props);

    GridEntities.forEach((entity) => {
      this.state.links.push(
        <li key={entity.id} className="main__menuitem">
          <NavLink exact to={'/admin/' + entity.id} activeClassName="menu__link--active" className="menu__link">{entity.name}</NavLink>
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
          <Tab className="tabs"/>
        </div>
      </main>
    );
  }
}

export default Main;