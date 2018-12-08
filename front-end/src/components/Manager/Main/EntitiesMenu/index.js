import React from 'react';
import { NavLink } from 'react-router-dom';
import GridEntities from '../../gridEntities';

function EntitiesMenu (props) {
  let links = [];

  GridEntities.forEach((entity) => {
    links.push(
      <li key={entity.id} className="main__menuitem">
        <NavLink exact to={'/manager/' + props.activeKey + '/' + entity.id} activeClassName="menu__link--active" className="menu__link">{entity.name}</NavLink>
      </li>
    );
  });

  return links;
}

export default EntitiesMenu;