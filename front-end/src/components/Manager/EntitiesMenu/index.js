import React from 'react';
import { NavLink } from 'react-router-dom';
import gridEntities from '../gridEntities';

function EntitiesMenu (props) {
  const links = [];

  gridEntities.forEach((entity) => {
    links.push(
      <NavLink to={'/manager/' + props.activeKey + '/' + entity.id} key={entity.id} className="configurator__link"
        activeClassName="configurator__link--active" onClick={() => props.setTabTitle(entity.name)}>{entity.name}</NavLink>
    );
  });

  return links;
}

export default EntitiesMenu;