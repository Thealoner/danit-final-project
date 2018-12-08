import React from 'react';
import { NavLink } from 'react-router-dom';
import gridEntities from '../gridEntities';

function EntitiesMenu (props) {
  const links = [];

  gridEntities.forEach((entity) => {
    let url = '/admin/' + props.activeKey + '/' + entity.id;

    links.push(
      <NavLink
        to={url}
        key={entity.id}
        className="configurator__link"
        activeClassName="configurator__link--active"
        onClick={(e) => props.addTab(e, url, entity.id, entity.name)}>
        {entity.name}
      </NavLink>
    );
  });

  return links;
}

export default EntitiesMenu;