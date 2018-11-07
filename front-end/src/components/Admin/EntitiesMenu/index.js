import React from 'react';
import { NavLink } from 'react-router-dom';
import GridEntities from '../GridEntities';

function EntitiesMenu (props) {
  let links = [];

  GridEntities.forEach((entity) => {
    links.push(
      <NavLink to={'/admin/' + props.activeKey + '/' + entity.id} key={entity.id} className="configurator__link"
        activeClassName="configurator__link--active" >{entity.name}</NavLink>
    );
  });

  return links;
}

export default EntitiesMenu;