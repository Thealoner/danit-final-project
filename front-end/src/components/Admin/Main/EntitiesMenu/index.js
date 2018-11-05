import React from 'react';
import { NavLink } from 'react-router-dom';
import GridEntities from '../GridEntities';

function EntitiesMenu (props) {
  let links = [];

  GridEntities.forEach((entity) => {
    links.push(
      <li key={entity.id} className="main__menuitem">
        <NavLink exact to={'/admin/' + props.activeKey + '/' + entity.id} activeClassName="menu__link--active" className="menu__link">{entity.name}</NavLink>
      </li>
    );
  });

  return links;
}

export default EntitiesMenu;